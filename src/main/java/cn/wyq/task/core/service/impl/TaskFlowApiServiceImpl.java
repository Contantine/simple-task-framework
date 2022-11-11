package cn.wyq.task.core.service.impl;

import cn.wyq.task.core.action.Action;
import cn.wyq.task.core.enums.TaskNodeStatusEnum;
import cn.wyq.task.core.enums.TaskStatusEnum;
import cn.wyq.task.core.exception.FlowException;
import cn.wyq.task.core.factory.TaskFlowServiceFactory;
import cn.wyq.task.core.model.*;
import cn.wyq.task.core.service.*;
import cn.wyq.task.core.util.SpringContextUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class TaskFlowApiServiceImpl implements TaskFlowApiService {
    private static final String TERMINATE_FLAG = "terminate_flag";

    @Autowired
    private TaskDefinitionService taskDefinitionService;

    @Autowired
    private TaskDefinitionNodeService taskDefinitionNodeService;

    @Autowired
    private TaskInstanceService taskInstanceService;

    @Autowired
    private TaskInstanceNodeService taskInstanceNodeService;

    @Autowired
    private TaskVariableService taskVariableService;

    @Autowired
    private TaskNodeVariableService taskNodeVariableService;

    @Override
    public String start(String definitionCode, Map<String, Object> variableMap) {
        TaskDefinition definition = taskDefinitionService.checkDefinitionCode(definitionCode);
        if (definition == null) {
            throw new FlowException("任务定义不存在");
        }
        // 生成任务实例
        TaskInstance instance = taskInstanceService.generateTaskInstance(definitionCode);

        // 变量转换
        List<TaskVariable> variableList = TaskVariable.convert(variableMap, instance.getInstanceCode());

        // 保存任务全局数据
        taskVariableService.saveBatch(variableList);

        // 获取第一个流程节点
        TaskDefinitionNode nextDefinitionNode = taskDefinitionNodeService.findNextDefinitionNode(definitionCode, null, variableList, null);

        // 生成任务节点
        Long nodeId = taskInstanceNodeService.generateTaskInstanceNode(instance, 0L, nextDefinitionNode);

        instance.setNodeId(nodeId);
        taskInstanceService.updateById(instance);

        TaskFlowServiceFactory.load(definitionCode, definition.getFlowClass());

        return instance.getInstanceCode();
    }

    @Override
    public TaskInstance process(String taskCode, Map<String, Object> variableMap) {
        TaskInstanceNode instanceNode = checkInstanceNode(taskCode);

        TaskInstance instance = checkInstance(instanceNode.getInstanceCode(), instanceNode.getNodeId());

        TaskDefinitionNode definitionNode = taskDefinitionNodeService.getOne(new LambdaQueryWrapper<TaskDefinitionNode>()
                .eq(TaskDefinitionNode::getNodeCode, instanceNode.getNodeCode()));
        Object action = SpringContextUtil.getBeanByFullClass(definitionNode.getActionClass());
        if (action instanceof Action) {
            Map<String, Object> result = ((Action) action).doProgress(instance.getInstanceCode(), variableMap);

            // 保存节点变量
            List<TaskNodeVariable> nodeVariableList = TaskNodeVariable.convert(variableMap, instance.getInstanceCode(), taskCode);
            taskNodeVariableService.saveBatch(nodeVariableList, nodeVariableList.size());

            if (null != result) {
                boolean terminateFlag = "true".equals(result.remove(TERMINATE_FLAG));
                // 更新任务全局数据
                List<TaskVariable> variableList = TaskVariable.convert(result, instance.getInstanceCode());
                taskVariableService.upsert(variableList);

                // 更新节点信息
                instanceNode.setActionStatus(TaskNodeStatusEnum.FINISHED.getStatus());
                instanceNode.setActionTime(new Date());
                taskInstanceNodeService.updateById(instanceNode);

                if (terminateFlag) {
                    doTerminate(instance);
                    return instance;
                }
            }

            // 获取任务全局数据
            List<TaskVariable> variableList = taskVariableService.list(new LambdaQueryWrapper<TaskVariable>()
                    .eq(TaskVariable::getInstanceCode, instance.getInstanceCode()));


            // 获取下一个流程节点
            TaskDefinitionNode nextDefinitionNode = taskDefinitionNodeService.findNextDefinitionNode(instance.getDefinitionCode()
                    , definitionNode.getNodeCode(), variableList, nodeVariableList);

            // 没有下一个节点,任务结束
            if (nextDefinitionNode == null) {
                doFinish(instance);
                return instance;
            }

            // 生成任务节点
            Long nodeId = taskInstanceNodeService.generateTaskInstanceNode(instance, instanceNode.getNodeId(), nextDefinitionNode);

            instance.setNodeId(nodeId);
            taskInstanceService.updateById(instance);

        } else {
            throw new FlowException(String.format("任务节点定义: %s, 处理类型错误", definitionNode.getNodeCode()));
        }
        return instance;
    }

    public void doTerminate(TaskInstance instance) {
        instance.setStatus(TaskStatusEnum.TERMINATED.getStatus());
        taskInstanceService.updateById(instance);

        TaskFlowService service = TaskFlowServiceFactory.getService(instance.getDefinitionCode());
        service.onTerminated(instance.getInstanceCode());
    }

    public void doFinish(TaskInstance instance) {
        instance.setStatus(TaskStatusEnum.FINISHED.getStatus());
        taskInstanceService.updateById(instance);

        TaskFlowService service = TaskFlowServiceFactory.getService(instance.getDefinitionCode());
        service.onFinished(instance.getInstanceCode());
    }

    public TaskInstanceNode checkInstanceNode(String taskNode) {
        TaskInstanceNode instanceNode = taskInstanceNodeService.getByTaskCode(taskNode);
        if (instanceNode == null || TaskNodeStatusEnum.WAIT.getStatus() != instanceNode.getActionStatus()) {
            throw new FlowException("任务不存在");
        }
        return instanceNode;
    }

    public TaskInstance checkInstance(String instanceCode, Long nodeId) {
        TaskInstance instance = taskInstanceService.getByInstanceCode(instanceCode);
        if (instance == null || TaskStatusEnum.IN_PROGRESS.getStatus() != instance.getStatus()) {
            throw new FlowException("任务不存在");
        }
        if (instance.getNodeId() > nodeId) {
            throw new FlowException("任务已被他人处理");
        }
        return instance;
    }

}
