package cn.wyq.task.core.model;

import cn.wyq.task.core.entity.BaseEntity;
import lombok.Data;

/**
 * 任务定义类
 */
@Data
public class TaskDefinition extends BaseEntity {
    /**
     * 定义代码
     */
    private String definitionCode;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务拓展处理类, 可以为 null
     */
    private String flowClass;
}
