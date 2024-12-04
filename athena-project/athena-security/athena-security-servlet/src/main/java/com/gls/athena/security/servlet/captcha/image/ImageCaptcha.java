package com.gls.athena.security.servlet.captcha.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gls.athena.security.servlet.captcha.base.BaseCaptcha;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.BufferedImage;

/**
 * 图片验证码
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageCaptcha extends BaseCaptcha {
    /**
     * 图片
     */
    @JsonIgnore
    private BufferedImage image;
}
