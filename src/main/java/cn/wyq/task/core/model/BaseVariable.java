package cn.wyq.task.core.model;


import cn.wyq.task.core.entity.BaseEntity;
import lombok.Data;

/**
 * 基础变量类
 */
@Data
public class BaseVariable extends BaseEntity {
    /**
     * 变量名称
     */
    private String key;
    /**
     * 变量值
     */
    private String value;
    /**
     * 变量类型
     */
    private String type;
}
