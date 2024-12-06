package com.gls.athena.security.servlet.captcha.base;

import com.gls.athena.security.servlet.captcha.CaptchaAuthenticationException;
import com.gls.athena.security.servlet.captcha.repository.ICaptchaRepository;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码提供器
 *
 * @param <Captcha> 验证码类型
 * @author george
 */
@Data
@Accessors(chain = true)
public abstract class BaseCaptchaProvider<Captcha extends BaseCaptcha> {
    /**
     * 验证码存储器
     */
    private ICaptchaRepository repository;
    /**
     * 验证码生成器
     */
    private ICaptchaGenerator<Captcha> generator;
    /**
     * 验证码发送器
     */
    private ICaptchaSender<Captcha> sender;

    /**
     * 发送验证码
     *
     * @param request 请求
     */
    public void send(ServletWebRequest request) {
        // 获取接收目标
        String target = getTarget(request);
        // 生成验证码
        Captcha captcha = generator.generate();
        // 保存验证码
        repository.save(target, captcha);
        // 发送验证码
        sender.send(target, captcha, request.getResponse());
    }

    /**
     * 验证验证码
     *
     * @param request 请求
     */
    public void verify(ServletWebRequest request) {
        // 获取接收目标和验证码
        String target = getTarget(request);
        String code = getCode(request);
        // 获取验证码
        BaseCaptcha baseCaptcha = repository.get(target);
        // 验证码不存在或已过期
        if (baseCaptcha == null) {
            throw new CaptchaAuthenticationException("验证码不存在或已过期");
        }
        // 验证码正确且未过期
        if (baseCaptcha.getCode().equals(code)
                && baseCaptcha.getExpireTime().getTime() > System.currentTimeMillis()) {
            repository.remove(target);
        } else {
            // 验证码错误
            throw new CaptchaAuthenticationException("验证码错误");
        }
    }

    /**
     * 是否支持
     *
     * @param request 请求
     * @return 是否支持
     */
    public boolean support(ServletWebRequest request) {
        if (isSendRequest(request)) {
            return true;
        }
        return isVerifyRequest(request);
    }

    /**
     * 是否是发送请求
     *
     * @param request 请求
     * @return 是否是发送请求
     */
    public abstract boolean isSendRequest(ServletWebRequest request);

    /**
     * 是否是校验请求
     *
     * @param request 请求
     * @return 是否是校验请求
     */
    public abstract boolean isVerifyRequest(ServletWebRequest request);

    /**
     * 获取接收目标
     *
     * @param request 请求
     * @return 接收目标
     */
    public abstract String getTarget(ServletWebRequest request);

    /**
     * 获取验证码
     *
     * @param request 请求
     * @return 验证码
     */
    public abstract String getCode(ServletWebRequest request);

}
