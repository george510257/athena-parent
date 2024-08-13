package com.athena.security.core.common.code;

import com.athena.common.core.constant.BaseConstants;
import com.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".security.verification-code")
public class VerificationCodeProperties extends BaseProperties {

    /**
     * 短信验证码配置
     */
    private Sms sms = new Sms();

    /**
     * 图形验证码配置
     */
    private Image image = new Image();

    @Data
    public static class Sms implements Serializable {
        /**
         * 验证码长度
         */
        private int length = 6;
        /**
         * 过期时间
         */
        private int expireIn = 60;
        /**
         * 验证码参数名
         */
        private String codeParameterName = "smsCode";
        /**
         * 手机号参数名
         */
        private String targetParameterName = "mobile";
        /**
         * 获取短信验证码url
         */
        private String url = "/code/sms";
        /**
         * 需要校验验证码的url
         */
        private List<String> urls = new ArrayList<>();
    }

    @Data
    public static class Image implements Serializable {
        /**
         * 验证码长度
         */
        private int length = 6;
        /**
         * 过期时间
         */
        private int expireIn = 60;
        /**
         * 图形验证码宽度
         */
        private int width = 80;
        /**
         * 图形验证码高度
         */
        private int height = 30;
        /**
         * 图形验证码干扰线数量
         */
        private int lineCount = 40;
        /**
         * 图形验证码字体大小
         */
        private int fontSize = 20;
        /**
         * 验证码参数名
         */
        private String codeParameterName = "imageCode";
        /**
         * 验证码key参数名
         */
        private String targetParameterName = "imageCodeKey";
        /**
         * 获取图形验证码url
         */
        private String url = "/code/image";
        /**
         * 需要校验验证码的url
         */
        private List<String> urls = new ArrayList<>();
    }
}
