package cn.wyq.task.core.service.impl;

import cn.wyq.task.core.mapper.TaskVariableMapper;
import cn.wyq.task.core.model.BaseVariable;
import cn.wyq.task.core.model.TaskVariable;
import cn.wyq.task.core.service.TaskVariableService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskVariableServiceImpl extends ServiceImpl<TaskVariableMapper, TaskVariable>
        implements TaskVariableService {
    @Override
    public void upsert(List<TaskVariable> variableList) {
        List<TaskVariable> insertList = new ArrayList<>();
        for (TaskVariable taskVariable : variableList) {
            TaskVariable record = getOne(new LambdaQueryWrapper<TaskVariable>().eq(BaseVariable::getKey, taskVariable.getKey()));
            if (null == record) {
                insertList.add(taskVariable);
            } else {
                update(new LambdaUpdateWrapper<TaskVariable>()
                        .eq(TaskVariable::getKey, taskVariable.getKey())
                        .set(TaskVariable::getValue, taskVariable.getValue())
                        .set(TaskVariable::getType, taskVariable.getType()));
            }
        }
        saveBatch(insertList, insertList.size());
    }
}
