package cn.wyq.task.core.model;

import cn.wyq.task.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 任务实例节点
 */
@Data
public class TaskInstanceNode extends BaseEntity {
    /**
     * 节点Id
     */
    private Long nodeId;
    /**
     * 父节点id
     */
    private Long parentNodeId;
    /**
     * 实例代码
     */
    private String instanceCode;
    /**
     * 任务代码
     */
    private String taskCode;
    /**
     * 节点代码
     */
    private String nodeCode;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 操作人
     */
    private String actionUser;
    /**
     * 状态
     * @see cn.wyq.task.core.enums.TaskNodeStatusEnum
     */
    private Integer actionStatus;
    /**
     * 操作时间
     */
    private Date actionTime;
    /**
     * 备注
     */
    private String remark;
}
