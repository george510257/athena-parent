package com.athena.security.core.common.code.image;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateUtil;
import com.athena.security.core.common.SecurityProperties;
import com.athena.security.core.common.code.base.VerificationCodeGenerator;

/**
 * 图片验证码生成器
 */
public class ImageCodeGenerator implements VerificationCodeGenerator<ImageCode> {
    /**
     * 图片验证码配置
     */
    private final SecurityProperties.Image imageProperties;

    /**
     * 构造函数
     *
     * @param securityProperties 安全配置
     */
    public ImageCodeGenerator(SecurityProperties securityProperties) {
        this.imageProperties = securityProperties.getCode().getImage();
    }

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    @Override
    public ImageCode generate() {
        // 创建线段干扰的验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(imageProperties.getWidth(), imageProperties.getHeight(), imageProperties.getLength(), imageProperties.getLineCount(), imageProperties.getFontSize());
        // 创建图片验证码
        ImageCode imageCode = new ImageCode();
        imageCode.setCode(lineCaptcha.getCode());
        imageCode.setImage(lineCaptcha.getImage());
        imageCode.setExpireTime(DateUtil.offsetSecond(DateUtil.date(), imageProperties.getExpireIn()));
        return imageCode;
    }
}
