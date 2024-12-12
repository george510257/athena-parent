package com.gls.athena.starter.log.support;

import com.gls.athena.starter.log.config.LogProperties;
import com.gls.athena.starter.log.method.IMethodEventListener;
import com.gls.athena.starter.log.method.MethodEvent;
import com.gls.athena.starter.log.method.MethodLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 方法日志消费者
 *
 * @author george
 */
@Slf4j
@RequiredArgsConstructor
public class KafkaMethodEventListener implements IMethodEventListener {
    /**
     * 日志配置
     */
    private final LogProperties logProperties;
    /**
     * kafka模板
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;
    /**
     * 应用名称
     */
    private final String applicationName;

    /**
     * 方法事件监听
     *
     * @param event 方法事件
     */
    @Override
    public void onMethodEvent(MethodEvent event) {
        String key = getKafkaKey(event);
        MethodLogDto methodLogDto = getMethodLogDto(event);
        log.info("发送方法日志: {}", methodLogDto);
        kafkaTemplate.send(logProperties.getKafka().getTopic(), key, methodLogDto);
    }

    /**
     * 获取方法日志DTO
     *
     * @param event 方法事件
     * @return 方法日志DTO
     */
    private MethodLogDto getMethodLogDto(MethodEvent event) {
        MethodLogDto methodLogDto = new MethodLogDto();
        methodLogDto.setCode(event.getCode());
        methodLogDto.setName(event.getName());
        methodLogDto.setDescription(event.getDescription());
        methodLogDto.setApplicationName(applicationName);
        methodLogDto.setClassName(event.getClassName());
        methodLogDto.setMethodName(event.getMethodName());
        if (event instanceof MethodLogEvent logEvent) {
            methodLogDto.setArgs(logEvent.getArgs());
            methodLogDto.setResult(logEvent.getResult());
            methodLogDto.setStartTime(logEvent.getStartTime());
            methodLogDto.setEndTime(logEvent.getEndTime());
            methodLogDto.setErrorMessage(logEvent.getErrorMessage());
            methodLogDto.setThrowable(logEvent.getThrowable());
            methodLogDto.setType(logEvent.getType().name());
            methodLogDto.setTraceId(logEvent.getTraceId());
        }
        return methodLogDto;
    }

    /**
     * 获取kafka key
     *
     * @param event 方法事件
     * @return kafka key
     */
    private String getKafkaKey(MethodEvent event) {
        if (event instanceof MethodLogEvent) {
            return logProperties.getKafka().getMethodLogKey();
        }
        return logProperties.getKafka().getMethodKey();
    }

}
