package com.athena.security.servlet.code.image;

import com.athena.security.servlet.code.VerificationCodeException;
import com.athena.security.servlet.code.base.BaseCodeSender;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * 图片验证码发送器
 *
 * @author george
 */
@Slf4j
public class ImageCodeSender implements BaseCodeSender<ImageCode> {
    /**
     * 发送图片验证码
     *
     * @param target   接收目标
     * @param code     验证码
     * @param response 响应
     */
    @Override
    public void send(String target, ImageCode code, HttpServletResponse response) {
        try {
            ImageIO.write(code.getImage(), "PNG", response.getOutputStream());
        } catch (IOException e) {
            throw new VerificationCodeException("图片验证码发送失败", e);
        }
    }
}
