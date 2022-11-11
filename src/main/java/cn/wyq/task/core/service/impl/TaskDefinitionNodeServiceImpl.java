package cn.wyq.task.core.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wyq.task.core.condition.Condition;
import cn.wyq.task.core.mapper.TaskDefinitionNodeMapper;
import cn.wyq.task.core.model.TaskDefinitionNode;
import cn.wyq.task.core.model.TaskNodeVariable;
import cn.wyq.task.core.model.TaskVariable;
import cn.wyq.task.core.service.TaskDefinitionNodeService;
import cn.wyq.task.core.util.SpringContextUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskDefinitionNodeServiceImpl extends ServiceImpl<TaskDefinitionNodeMapper, TaskDefinitionNode>
        implements TaskDefinitionNodeService {
    @Override
    public TaskDefinitionNode findNextDefinitionNode(String definitionCode, String parentCode
            , List<TaskVariable> variableList, List<TaskNodeVariable> nodeVariableList) {
        List<TaskDefinitionNode> definitionNodes = baseMapper.selectList(new LambdaQueryWrapper<TaskDefinitionNode>()
                .eq(TaskDefinitionNode::getDefinitionCode, definitionCode)
                .eq(TaskDefinitionNode::getParentNodeCode, parentCode)
                .orderByAsc(TaskDefinitionNode::getPriority));

        for (TaskDefinitionNode definitionNode : definitionNodes) {
            if (StrUtil.isEmpty(definitionNode.getConditionClass())) {
                return definitionNode;
            }
            Object condition = SpringContextUtil.getBeanByFullClass(definitionNode.getConditionClass());
            if (condition instanceof Condition) {
                if (((Condition) condition).checkCondition(variableList, nodeVariableList)) {
                    return definitionNode;
                }
            }
        }
        return null;
    }
}
