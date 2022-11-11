package cn.wyq.task.core.dto;

import lombok.Data;

/**
 * 节点任务DTO
 */
@Data
public class TaskNodeDTO {
    /**
     * 定义代码
     */
    private String definitionCode;
    /**
     * 实例代码
     */
    private String instanceCode;
    /**
     * 节点代码
     */
    private String nodeCode;
}
