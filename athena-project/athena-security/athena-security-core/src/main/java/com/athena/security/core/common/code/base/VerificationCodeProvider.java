package com.athena.security.core.common.code.base;

import com.athena.security.core.common.code.VerificationCodeException;
import com.athena.security.core.common.code.repository.VerificationCodeRepository;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码提供器
 *
 * @param <VC> 验证码类型
 */
public abstract class VerificationCodeProvider<VC extends VerificationCode> {
    /**
     * 验证码存储器
     */
    private final VerificationCodeRepository verificationCodeRepository;
    /**
     * 验证码生成器
     */
    private final VerificationCodeGenerator<VC> verificationCodeGenerator;
    /**
     * 验证码发送器
     */
    private final VerificationCodeSender<VC> verificationCodeSender;

    /**
     * 构造函数
     *
     * @param verificationCodeRepository 存储器
     * @param verificationCodeGenerator  生成器
     * @param verificationCodeSender     发送器
     */
    public VerificationCodeProvider(VerificationCodeRepository verificationCodeRepository,
                                    VerificationCodeGenerator<VC> verificationCodeGenerator,
                                    VerificationCodeSender<VC> verificationCodeSender) {
        this.verificationCodeRepository = verificationCodeRepository;
        this.verificationCodeGenerator = verificationCodeGenerator;
        this.verificationCodeSender = verificationCodeSender;
    }

    /**
     * 发送验证码
     *
     * @param request 请求
     */
    public void send(ServletWebRequest request) {
        // 获取接收目标
        String target = getTarget(request);
        // 生成验证码
        VC code = verificationCodeGenerator.generate();
        // 保存验证码
        verificationCodeRepository.save(target, code);
        // 发送验证码
        verificationCodeSender.send(target, code);
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
        VerificationCode verificationCode = verificationCodeRepository.get(target);
        // 验证码不存在或已过期
        if (verificationCode == null) {
            throw new VerificationCodeException("验证码不存在或已过期");
        }
        // 验证码正确且未过期
        if (verificationCode.getCode().equals(code)
                && verificationCode.getExpireTime().getTime() > System.currentTimeMillis()) {
            verificationCodeRepository.remove(target);
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
