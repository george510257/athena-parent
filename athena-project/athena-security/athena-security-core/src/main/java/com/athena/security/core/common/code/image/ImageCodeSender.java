package com.athena.security.core.common.code.image;

import com.athena.common.core.util.WebUtil;
import com.athena.security.core.common.code.base.VerificationCodeSender;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.io.OutputStream;

/**
 * 图片验证码发送器
 */
public class ImageCodeSender implements VerificationCodeSender<ImageCode> {
    /**
     * 发送图片验证码
     *
     * @param target 接收目标
     * @param code   验证码
     */
    @SneakyThrows
    @Override
    public void send(String target, ImageCode code) {
        // 获取response输出流
        OutputStream out = WebUtil.getOutputStream();
        // 将图片写到response.getOutputStream()中
        ImageIO.write(code.getImage(), "JPEG", out);
    }
}
