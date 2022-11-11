package cn.wyq.task.core.service;

import cn.wyq.task.core.model.TaskDefinitionNode;
import cn.wyq.task.core.model.TaskNodeVariable;
import cn.wyq.task.core.model.TaskVariable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TaskDefinitionNodeService extends IService<TaskDefinitionNode> {
    TaskDefinitionNode findNextDefinitionNode(String definitionCode, String parentCode, List<TaskVariable> variableList
            , List<TaskNodeVariable> nodeVariableList);
}
