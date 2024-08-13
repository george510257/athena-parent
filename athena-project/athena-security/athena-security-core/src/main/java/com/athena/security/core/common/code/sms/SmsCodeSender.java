package com.athena.security.core.common.code.sms;

import com.athena.security.core.common.code.base.VerificationCodeSender;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信验证码发送器
 */
@Slf4j
public class SmsCodeSender implements VerificationCodeSender<SmsCode> {

    /**
     * 发送短信验证码
     *
     * @param target 接收目标
     * @param code   验证码
     */
    @Override
    public void send(String target, SmsCode code) {
        log.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
        log.info("向手机{}发送短信验证码{}", target, code.getCode());
    }
}
