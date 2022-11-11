package cn.wyq.task.core.service;

import cn.wyq.task.core.model.TaskInstance;

import java.util.Map;

/**
 * 任务流程
 */
public interface TaskFlowApiService {
    /**
     * 开启任务
     * @param definitionCode 定义代码
     * @param variableMap 处理数据
     * @return 任务实例代码
     */
    String start(String definitionCode, Map<String, Object> variableMap);

    /**
     * 任务处理
     * @param taskCode 任务代码
     * @param variableMap 处理数据
     * @return 任务实例
     */
    TaskInstance process(String taskCode, Map<String, Object> variableMap);
}
