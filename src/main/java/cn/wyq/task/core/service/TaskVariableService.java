package cn.wyq.task.core.service;

import cn.wyq.task.core.model.TaskVariable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TaskVariableService extends IService<TaskVariable> {
    void upsert(List<TaskVariable> variableList);
}
