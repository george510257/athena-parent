package com.gls.athena.starter.aliyun.sms.support;

import cn.hutool.extra.spring.SpringUtil;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.gls.athena.starter.aliyun.sms.config.AliyunSmsProperties;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 阿里云短信工具类
 *
 * @author george
 */
@Slf4j
@UtilityClass
public class AliyunSmsHelper {

    /**
     * 发送短信
     *
     * @param phone        手机号
     * @param templateCode 模板编号
     * @param params       参数
     */
    public void sendSms(String phone, String templateCode, String params) throws ClientException {
        IAcsClient acsClient = SpringUtil.getBean("smsAcsClient");
        AliyunSmsProperties properties = SpringUtil.getBean(AliyunSmsProperties.class);
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName(properties.getSignName());
        request.setTemplateCode(templateCode);
        request.setTemplateParam(params);
        SendSmsResponse response = acsClient.getAcsResponse(request);
        if (!"OK".equals(response.getCode())) {
            log.error("发送短信失败，错误码：{}，错误信息：{}", response.getCode(), response.getMessage());
            throw new ClientException(response.getMessage());
        }
    }
}
