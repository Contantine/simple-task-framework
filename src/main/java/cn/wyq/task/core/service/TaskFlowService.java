package cn.wyq.task.core.service;

/**
 * 任务流程可拓展通用功能
 */
public interface TaskFlowService {
    /**
     * 任务结束时调用
     */
    default void onFinished(String instanceCode) {

    }


    /**
     * 任务被终止时调用
     */
    default void onTerminated(String instanceCode) {

    }



    /**
     * 返回任务情况数据
     */
    default Object getApplyForm(String instanceCode) {
        return null;
    }
}
