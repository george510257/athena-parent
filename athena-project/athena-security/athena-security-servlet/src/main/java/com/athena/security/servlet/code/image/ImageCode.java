package com.athena.security.servlet.code.image;

import com.athena.security.servlet.code.base.VerificationCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.BufferedImage;

/**
 * 图片验证码
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageCode extends VerificationCode {
    /**
     * 图片
     */
    @JsonIgnore
    private BufferedImage image;
}
