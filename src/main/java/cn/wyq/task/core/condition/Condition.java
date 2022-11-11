package cn.wyq.task.core.condition;

import cn.wyq.task.core.model.TaskNodeVariable;
import cn.wyq.task.core.model.TaskVariable;

import java.util.List;

/**
 * 任务节点条件判断
 */
public interface Condition {
    /**
     *
     * @param variables 任务变量
     * @return 是否满足该任务节点
     */
    boolean checkCondition(List<TaskVariable> variables, List<TaskNodeVariable> nodeVariables);
}
