package cn.wyq.task.core.exception;

/**
 * 常规流程异常
 */
public class FlowException extends RuntimeException{
    public FlowException(String message) {
        super(message);
    }
}
