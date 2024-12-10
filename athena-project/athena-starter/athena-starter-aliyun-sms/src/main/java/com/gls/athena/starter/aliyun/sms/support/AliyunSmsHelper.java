package com.gls.athena.starter.aliyun.sms.support;

import cn.hutool.extra.spring.SpringUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import lombok.experimental.UtilityClass;

/**
 * 短信助手
 *
 * @author george
 */
@UtilityClass
public class AliyunSmsHelper {

    /**
     * 发送短信
     *
     * @param phone         手机号
     * @param templateCode  模板代码
     * @param templateParam 模板参数
     * @return 发送结果
     * @throws Exception 异常
     */
    public Boolean send(String phone, String templateCode, String templateParam) throws Exception {
        // 创建发送短信的请求
        // 发送短信
        SendSmsRequest request = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setTemplateCode(templateCode)
                .setTemplateParam(templateParam);
        // 获取短信客户端
        Client client = SpringUtil.getBean(Client.class);
        // 发送短信
        SendSmsResponse response = client.sendSms(request);
        // 如果发送失败返回
        return "OK".equals(response.getBody().getCode());
    }
}
