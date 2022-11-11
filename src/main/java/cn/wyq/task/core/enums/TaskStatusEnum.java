package cn.wyq.task.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务实例状态枚举
 */
@Getter
@AllArgsConstructor
public enum TaskStatusEnum {
    IN_PROGRESS(1, "进行中"),
    FINISHED(2, "已完成"),
    TERMINATED(3, "已终止");

    int status;

    String name;
}
