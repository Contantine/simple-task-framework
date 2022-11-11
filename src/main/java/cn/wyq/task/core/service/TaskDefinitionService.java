package cn.wyq.task.core.service;

import cn.wyq.task.core.model.TaskDefinition;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TaskDefinitionService extends IService<TaskDefinition> {
    /**
     * 校验定义代码
     * @param definitionCode 定义代码
     * @return 是否有效
     */
    TaskDefinition checkDefinitionCode(String definitionCode);
}
