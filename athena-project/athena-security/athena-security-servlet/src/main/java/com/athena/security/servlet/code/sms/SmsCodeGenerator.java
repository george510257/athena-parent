package com.athena.security.servlet.code.sms;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.athena.security.core.properties.CoreSecurityProperties;
import com.athena.security.servlet.code.base.BaseCodeGenerator;
import lombok.RequiredArgsConstructor;

/**
 * 短信验证码生成器
 */
@RequiredArgsConstructor
public class SmsCodeGenerator implements BaseCodeGenerator<SmsCode> {
    /**
     * 短信验证码配置
     */
    private final CoreSecurityProperties properties;

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    @Override
    public SmsCode generate() {
        CoreSecurityProperties.Sms sms = properties.getVerificationCode().getSms();
        SmsCode smsCode = new SmsCode();
        smsCode.setCode(RandomUtil.randomNumbers(sms.getLength()));
        smsCode.setExpireTime(DateUtil.offsetSecond(DateUtil.date(), sms.getExpireIn()).toJdkDate());
        return smsCode;
    }

}
