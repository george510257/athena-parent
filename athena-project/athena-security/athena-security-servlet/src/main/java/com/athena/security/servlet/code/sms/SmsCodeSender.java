package com.athena.security.servlet.code.sms;

import cn.hutool.json.JSONUtil;
import com.athena.common.bean.result.Result;
import com.athena.common.bean.result.ResultStatus;
import com.athena.security.servlet.code.base.BaseCodeSender;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信验证码发送器
 *
 * @author george
 */
@Slf4j
public class SmsCodeSender implements BaseCodeSender<SmsCode> {

    /**
     * 发送短信验证码
     *
     * @param target   接收目标
     * @param code     验证码
     * @param response 响应
     */
    @Override
    public void send(String target, SmsCode code, HttpServletResponse response) {
        log.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
        log.info("向手机{}发送短信验证码{}", target, code.getCode());
        Result<String> result = ResultStatus.SUCCESS.toResult("短信验证码发送成功");
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONUtil.toJsonStr(result));
        } catch (Exception e) {
            log.error("短信验证码发送失败", e);
        }
    }
}
