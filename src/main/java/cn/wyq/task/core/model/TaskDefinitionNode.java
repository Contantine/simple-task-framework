package cn.wyq.task.core.model;

import cn.wyq.task.core.entity.BaseEntity;
import lombok.Data;

/**
 * 任务定义节点
 */
@Data
public class TaskDefinitionNode extends BaseEntity {
    /**
     * 定义代码
     */
    private String definitionCode;
    /**
     * 父节点代码
     */
    private String parentNodeCode;
    /**
     * 节点代码
     */
    private String nodeCode;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 条件判断相关类(全路径)
     */
    private String conditionClass;
    /**
     * 操作类(全路径)
     */
    private String actionClass;
    /**
     * 节点优先级
     */
    private Integer priority;
}
