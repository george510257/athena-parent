package com.gls.athena.security.servlet.captcha.sms;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.gls.athena.security.servlet.captcha.base.ICaptchaGenerator;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 短信验证码生成器
 *
 * @author george
 */
@Setter
@Accessors(chain = true)
public class SmsCaptchaGenerator implements ICaptchaGenerator<SmsCaptcha> {
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
    public SmsCaptcha generate() {
        SmsCaptcha smsCaptcha = new SmsCaptcha();
        smsCaptcha.setCode(RandomUtil.randomNumbers(length));
        smsCaptcha.setExpireTime(DateUtil.offsetSecond(DateUtil.date(), expireIn).toJdkDate());
        return smsCaptcha;
    }

}
