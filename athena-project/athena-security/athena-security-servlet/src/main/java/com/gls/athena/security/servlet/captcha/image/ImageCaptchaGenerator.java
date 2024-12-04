package com.gls.athena.security.servlet.captcha.image;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateUtil;
import com.gls.athena.security.servlet.captcha.base.ICaptchaGenerator;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 图片验证码生成器
 *
 * @author george
 */
@Setter
@Accessors(chain = true)
public class ImageCaptchaGenerator implements ICaptchaGenerator<ImageCaptcha> {
    /**
     * 验证码长度
     */
    private int length = 6;
    /**
     * 过期时间
     */
    private int expireIn = 600;
    /**
     * 图形验证码宽度
     */
    private int width = 100;
    /**
     * 图形验证码高度
     */
    private int height = 30;
    /**
     * 图形验证码干扰线数量
     */
    private int lineCount = 150;
    /**
     * 图形验证码字体大小
     */
    private float fontSize = 0.75f;

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
