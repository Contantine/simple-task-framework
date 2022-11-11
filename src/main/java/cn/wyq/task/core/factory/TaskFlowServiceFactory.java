package cn.wyq.task.core.factory;

import cn.hutool.core.bean.BeanException;
import cn.wyq.task.core.service.TaskFlowService;
import cn.wyq.task.core.service.impl.DefaultTaskFlowServiceImpl;
import cn.wyq.task.core.util.SpringContextUtil;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskFlowServiceFactory {
    private static final Class<DefaultTaskFlowServiceImpl> DEFAULT_IMPL = DefaultTaskFlowServiceImpl.class;

    private static final Map<String, TaskFlowService> map = new ConcurrentHashMap<>(16);

    public static TaskFlowService load(String definitionCode, String clazzName) {
        TaskFlowService taskFlowService = map.get(definitionCode);
        if (taskFlowService != null && !(taskFlowService instanceof DefaultTaskFlowServiceImpl)) {
            return taskFlowService;
        }
        if (StringUtils.isEmpty(clazzName)) {
            return loadDefault(definitionCode);
        }
        TaskFlowService bean = SpringContextUtil.getBeanByFullClass(clazzName);
        if (bean == null) {
            return loadDefault(definitionCode);
        }
        return map.put(definitionCode, bean);
    }

    private static TaskFlowService loadDefault(String definitionCode) {
        TaskFlowService defaultImpl = SpringContextUtil.getBean(DEFAULT_IMPL);
        map.put(definitionCode, defaultImpl);
        return defaultImpl;
    }

    public static TaskFlowService getService(String definitionCode) {
        TaskFlowService taskFlowService = map.get(definitionCode);
        if (null == taskFlowService) {
            return loadDefault(definitionCode);
        }
        return taskFlowService;
    }
}
