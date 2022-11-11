package cn.wyq.task.core.model;

import cn.wyq.task.core.entity.BaseEntity;
import cn.wyq.task.core.util.VariableTypeHelper;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务变量
 */
@Data
public class TaskVariable extends BaseVariable {
    /**
     * 实例代码
     */
    private String instanceCode;


    public static List<TaskVariable> convert(Map<String, Object> variableMap, String instanceCode) {
        return VariableTypeHelper.getVariables(variableMap).stream().map(e -> {
            TaskVariable res = new TaskVariable();
            BeanUtils.copyProperties(e, res);
            res.setInstanceCode(instanceCode);
            return res;
        }).collect(Collectors.toList());
    }

}
