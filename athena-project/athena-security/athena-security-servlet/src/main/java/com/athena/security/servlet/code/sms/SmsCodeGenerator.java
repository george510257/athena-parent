package com.athena.security.servlet.code.sms;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.athena.security.servlet.code.VerificationCodeProperties;
import com.athena.security.servlet.code.base.VerificationCodeGenerator;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 短信验证码生成器
 */
@Setter
@Accessors(chain = true)
public class SmsCodeGenerator implements VerificationCodeGenerator<SmsCode> {
    /**
     * 短信验证码配置
     */
    private VerificationCodeProperties.Sms sms;

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    @Override
    public SmsCode generate() {
        SmsCode smsCode = new SmsCode();
        smsCode.setCode(RandomUtil.randomNumbers(sms.getLength()));
        smsCode.setExpireTime(DateUtil.offsetSecond(DateUtil.date(), sms.getExpireIn()).toJdkDate());
        return smsCode;
    }

}
