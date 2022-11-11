package cn.wyq.task.core.util;

import cn.hutool.core.util.ObjectUtil;
import cn.wyq.task.core.model.BaseVariable;
import cn.wyq.task.core.model.TaskVariable;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 变量类型工具类
 */
public class VariableTypeHelper {

    public static List<BaseVariable> getVariables(Map<String, Object> variables) {
        if (variables == null) {
            return Collections.EMPTY_LIST;
        }
        List<BaseVariable> variableList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            TaskVariable variable = new TaskVariable();
            if (ObjectUtil.isBasicType(value) || value instanceof BigDecimal) {
                variable.setType(value.getClass().getName());
            } else if(value instanceof Collection) {
                variable.setType(Collection.class.getName());
                variable.setValue(join((Collection) value));
            } else {
                variable.setType(Object.class.getName());
            }
            variable.setKey(key);
            if (variable.getValue() == null) {
                variable.setValue(value.toString());
            }
            variableList.add(variable);
        }
        return variableList;
    }

    private static String join(Collection value) {
        Set<?> set = new HashSet(value);
        return set.stream().map(Object::toString).collect(Collectors.joining(","));
    }

}
