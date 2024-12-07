package com.gls.athena.security.servlet.captcha.image;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateUtil;
import com.gls.athena.security.servlet.captcha.base.ICaptchaGenerator;
import lombok.RequiredArgsConstructor;

/**
 * 图片验证码生成器
 *
 * @author george
 */
@RequiredArgsConstructor
public class ImageCaptchaGenerator implements ICaptchaGenerator<ImageCaptcha> {
    /**
     * 验证码长度
     */
    private final int length;
    /**
     * 过期时间
     */
    private final int expireIn;
    /**
     * 图形验证码宽度
     */
    private final int width;
    /**
     * 图形验证码高度
     */
    private final int height;
    /**
     * 图形验证码干扰线数量
     */
    private final int lineCount;
    /**
     * 图形验证码字体大小
     */
    private final float fontSize;

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    @Override
    public ImageCaptcha generate() {
        // 创建线段干扰的验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height, length, lineCount, fontSize);
        // 创建图片验证码
        ImageCaptcha imageCaptcha = new ImageCaptcha();
        imageCaptcha.setCode(lineCaptcha.getCode());
        imageCaptcha.setImage(lineCaptcha.getImage());
        imageCaptcha.setExpireTime(DateUtil.offsetSecond(DateUtil.date(), expireIn).toJdkDate());
        return imageCaptcha;
    }
}
