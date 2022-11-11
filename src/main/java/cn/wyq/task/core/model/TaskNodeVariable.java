package cn.wyq.task.core.model;

import cn.wyq.task.core.entity.BaseEntity;
import cn.wyq.task.core.util.VariableTypeHelper;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务节点数据
 */
@Data
public class TaskNodeVariable extends BaseVariable {
    private String instanceCode;

    private String taskCode;

    public static List<TaskNodeVariable> convert(Map<String, Object> variableMap, String instanceCode, String taskCode) {
        return VariableTypeHelper.getVariables(variableMap).stream().map(e -> {
            TaskNodeVariable res = new TaskNodeVariable();
            BeanUtils.copyProperties(e, res);
            res.setInstanceCode(instanceCode);
            res.setTaskCode(taskCode);
            return res;
        }).collect(Collectors.toList());
    }
}
