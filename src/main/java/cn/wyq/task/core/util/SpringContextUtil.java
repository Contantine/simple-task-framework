package cn.wyq.task.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.context = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) context.getBean(clazz);
    }


    public static <T> T getBeanByFullClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return (T) getBean(clazz);
        }catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
