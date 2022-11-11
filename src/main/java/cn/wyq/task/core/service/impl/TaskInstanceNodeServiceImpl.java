package cn.wyq.task.core.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.wyq.task.core.action.Action;
import cn.wyq.task.core.dto.TaskNodeDTO;
import cn.wyq.task.core.enums.TaskNodeStatusEnum;
import cn.wyq.task.core.exception.FlowException;
import cn.wyq.task.core.mapper.TaskInstanceNodeMapper;
import cn.wyq.task.core.model.NodeUser;
import cn.wyq.task.core.model.TaskDefinitionNode;
import cn.wyq.task.core.model.TaskInstance;
import cn.wyq.task.core.model.TaskInstanceNode;
import cn.wyq.task.core.service.TaskInstanceNodeService;
import cn.wyq.task.core.util.SpringContextUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskInstanceNodeServiceImpl extends ServiceImpl<TaskInstanceNodeMapper, TaskInstanceNode>
        implements TaskInstanceNodeService {
    @Override
    public Long generateTaskInstanceNode(TaskInstance instance, Long parentNodeId, TaskDefinitionNode definitionNode) {
        Object action = SpringContextUtil.getBeanByFullClass(definitionNode.getActionClass());
        if (!(action instanceof Action)) {
            throw new FlowException(String.format("任务节点定义: %s, 处理类型错误", definitionNode.getNodeCode()));
        }
        TaskNodeDTO taskNodeDTO = new TaskNodeDTO();
        taskNodeDTO.setDefinitionCode(definitionNode.getDefinitionCode());
        taskNodeDTO.setNodeCode(definitionNode.getNodeCode());
        taskNodeDTO.setInstanceCode(definitionNode.getDefinitionCode());
        // 处理人员列表
        List<NodeUser> operatorList = ((Action) action).assign(taskNodeDTO);
        Long nodeId = parentNodeId + 1000L;

        List<TaskInstanceNode> instanceNodes = new ArrayList<>();
        for (NodeUser nodeUser : operatorList) {
            TaskInstanceNode instanceNode = new TaskInstanceNode();
            instanceNode.setInstanceCode(instance.getInstanceCode());
            instanceNode.setNodeCode(definitionNode.getNodeCode());
            instanceNode.setNodeName(definitionNode.getNodeName());
            instanceNode.setParentNodeId(parentNodeId);
            instanceNode.setNodeId(nodeId);
            instanceNode.setActionUser(nodeUser.getUserName());
            instanceNode.setTaskCode(RandomUtil.randomString(20));
            instanceNode.setActionStatus(TaskNodeStatusEnum.WAIT.getStatus());
            instanceNodes.add(instanceNode);
        }
        saveBatch(instanceNodes, instanceNodes.size());
        return nodeId;
    }

    @Override
    public TaskInstanceNode getByTaskCode(String taskCode) {
        return baseMapper.selectOne(new LambdaQueryWrapper<TaskInstanceNode>().eq(TaskInstanceNode::getTaskCode, taskCode));
    }
}
