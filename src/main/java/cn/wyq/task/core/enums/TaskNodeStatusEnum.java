package cn.wyq.task.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务实例节点状态枚举
 */
@Getter
@AllArgsConstructor
public enum TaskNodeStatusEnum {
    WAIT(1, "待处理"),
    FINISHED(2, "已完成");
    int status;

    String name;
}
