package com.athena.security.servlet.code.sms;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.athena.security.servlet.code.base.BaseCodeGenerator;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 短信验证码生成器
 */
@Setter
@Accessors(chain = true)
public class SmsCodeGenerator implements BaseCodeGenerator<SmsCode> {
    /**
     * 验证码长度
     */
    private int length = 6;
    /**
     * 过期时间
     */
    private int expireIn = 600;

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    @Override
    public SmsCode generate() {
        SmsCode smsCode = new SmsCode();
        smsCode.setCode(RandomUtil.randomNumbers(length));
        smsCode.setExpireTime(DateUtil.offsetSecond(DateUtil.date(), expireIn).toJdkDate());
        return smsCode;
    }

}
