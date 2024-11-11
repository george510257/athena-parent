package com.gls.athena.security.servlet.code.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gls.athena.security.servlet.code.base.BaseCode;
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
public class ImageCode extends BaseCode {
    /**
     * 图片
     */
    @JsonIgnore
    private BufferedImage image;
}
