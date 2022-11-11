package cn.wyq.task.core.model;

import cn.wyq.task.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 任务实体类
 */
@Data
public class TaskInstance extends BaseEntity {
    /**
     * 任务实例代码
     */
    private String instanceCode;
    /**
     * 任务定义代码
     */
    private String definitionCode;
    /**
     * 当前处在的节点id
     */
    private Long nodeId;
    /**
     * 任务状态
     * @see cn.wyq.task.core.enums.TaskStatusEnum
     */
    private Integer status;
    /**
     * 创建者名称
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date createTime;
}
