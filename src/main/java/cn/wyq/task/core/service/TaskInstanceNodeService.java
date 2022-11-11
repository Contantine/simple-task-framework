package cn.wyq.task.core.service;

import cn.wyq.task.core.model.TaskDefinitionNode;
import cn.wyq.task.core.model.TaskInstance;
import cn.wyq.task.core.model.TaskInstanceNode;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TaskInstanceNodeService extends IService<TaskInstanceNode> {
    /**
     * 生成任务节点实例
     * @param instance 任务实例
     * @param parentNodeId 上一个任务节点实例nodeid, 生成第一个就填0
     * @param definitionNode 任务节点定义
     */
    Long generateTaskInstanceNode(TaskInstance instance, Long parentNodeId, TaskDefinitionNode definitionNode);

    TaskInstanceNode getByTaskCode(String taskCode);
}
