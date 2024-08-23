package com.athena.security.servlet.code.image;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateUtil;
import com.athena.security.servlet.code.base.BaseCodeGenerator;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 图片验证码生成器
 */
@Setter
@Accessors(chain = true)
public class ImageCodeGenerator implements BaseCodeGenerator<ImageCode> {
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
    public ImageCode generate() {
        // 创建线段干扰的验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height, length, lineCount, fontSize);
        // 创建图片验证码
        ImageCode imageCode = new ImageCode();
        imageCode.setCode(lineCaptcha.getCode());
        imageCode.setImage(lineCaptcha.getImage());
        imageCode.setExpireTime(DateUtil.offsetSecond(DateUtil.date(), expireIn).toJdkDate());
        return imageCode;
    }
}
