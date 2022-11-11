package cn.wyq.task.core.service;

import cn.wyq.task.core.model.TaskInstance;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TaskInstanceService extends IService<TaskInstance> {
    TaskInstance generateTaskInstance(String definitionCode);

    TaskInstance getByInstanceCode(String instanceCode);
}
