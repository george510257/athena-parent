package com.gls.athena.starter.log.method;

import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * @author george
 */
@Component
public class MethodLogManager {
    /**
     * 事件发布器
     */
    @Resource
    private ApplicationEventPublisher publisher;

    /**
     * 方法日志事件监听
     *
     * @param event 方法日志事件
     */
    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Class<?> beanClass = applicationContext.getType(beanName);
            if (beanClass == null) {
                continue;
            }
            ReflectionUtils.doWithMethods(beanClass, method -> {
                if (method.isAnnotationPresent(MethodLog.class)) {
                    MethodLog methodLog = method.getAnnotation(MethodLog.class);
                    String className = method.getDeclaringClass().getName();
                    String methodName = method.getName();
                    publisher.publishEvent(new MethodEvent(this, methodLog, className, methodName));
                }
            });
        }
    }

}