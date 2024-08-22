package com.athena.security.servlet.code.image;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateUtil;
import com.athena.security.core.properties.CoreSecurityProperties;
import com.athena.security.servlet.code.base.BaseCodeGenerator;
import lombok.RequiredArgsConstructor;

/**
 * 图片验证码生成器
 */
@RequiredArgsConstructor
public class ImageCodeGenerator implements BaseCodeGenerator<ImageCode> {
    /**
     * 图片验证码配置
     */
    private final CoreSecurityProperties properties;

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    @Override
    public ImageCode generate() {
        CoreSecurityProperties.Image image = properties.getVerificationCode().getImage();
        // 创建线段干扰的验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(image.getWidth(), image.getHeight(), image.getLength(), image.getLineCount(), image.getFontSize());
        // 创建图片验证码
        ImageCode imageCode = new ImageCode();
        imageCode.setCode(lineCaptcha.getCode());
        imageCode.setImage(lineCaptcha.getImage());
        imageCode.setExpireTime(DateUtil.offsetSecond(DateUtil.date(), image.getExpireIn()).toJdkDate());
        return imageCode;
    }
}
