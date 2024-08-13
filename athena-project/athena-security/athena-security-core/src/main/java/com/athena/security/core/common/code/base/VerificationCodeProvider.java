package com.athena.security.core.common.code.base;

import com.athena.security.core.common.code.VerificationCodeException;
import com.athena.security.core.common.code.repository.VerificationCodeRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码提供器
 *
 * @param <V> 验证码类型
 */
public abstract class VerificationCodeProvider<V extends VerificationCode,
        G extends VerificationCodeGenerator<V>, S extends VerificationCodeSender<V>> {
    /**
     * 验证码存储器
     */
    @Resource
    private VerificationCodeRepository repository;
    /**
     * 验证码生成器
     */
    @Autowired
    private G generator;
    /**
     * 验证码发送器
     */
    @Autowired
    private S sender;

    /**
     * 发送验证码
     *
     * @param request 请求
     */
    public void send(ServletWebRequest request) {
        // 获取接收目标
        String target = getTarget(request);
        // 生成验证码
        V code = generator.generate();
        // 保存验证码
        repository.save(target, code);
        // 发送验证码
        sender.send(target, code, request.getResponse());
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
        VerificationCode verificationCode = repository.get(target);
        // 验证码不存在或已过期
        if (verificationCode == null) {
            throw new VerificationCodeException("验证码不存在或已过期");
        }
        // 验证码正确且未过期
        if (verificationCode.getCode().equals(code)
                && verificationCode.getExpireTime().getTime() > System.currentTimeMillis()) {
            repository.remove(target);
        } else {
            // 验证码错误
            throw new VerificationCodeException("验证码错误");
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
