package com.athena.security.core.common.code.sms;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.athena.security.core.common.code.VerificationCodeProperties;
import com.athena.security.core.common.code.base.VerificationCodeGenerator;

/**
 * 短信验证码生成器
 */
public class SmsCodeGenerator implements VerificationCodeGenerator<SmsCode> {
    /**
     * 短信验证码配置
     */
    private final VerificationCodeProperties.Sms smsProperties;

    /**
     * 构造函数
     *
     * @param verificationCodeProperties 安全配置
     */
    public SmsCodeGenerator(VerificationCodeProperties verificationCodeProperties) {
        this.smsProperties = verificationCodeProperties.getSms();
    }

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    @Override
    public SmsCode generate() {
        SmsCode smsCode = new SmsCode();
        smsCode.setCode(RandomUtil.randomNumbers(smsProperties.getLength()));
        smsCode.setExpireTime(DateUtil.offsetSecond(DateUtil.date(), smsProperties.getExpireIn()));
        return smsCode;
    }

}
