package com.gls.athena.security.servlet.captcha.sms;

import cn.hutool.json.JSONUtil;
import com.gls.athena.common.bean.result.Result;
import com.gls.athena.common.bean.result.ResultStatus;
import com.gls.athena.security.servlet.captcha.base.ICaptchaSender;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信验证码发送器
 *
 * @author george
 */
@Slf4j
public class SmsCaptchaSender implements ICaptchaSender<SmsCaptcha> {

    /**
     * 发送短信验证码
     *
     * @param target     接收目标
     * @param smsCaptcha 验证码
     * @param response   响应
     */
    @Override
    public void send(String target, SmsCaptcha smsCaptcha, HttpServletResponse response) {
        log.warn("请配置真实的短信验证码发送器(SmsCaptchaSender)");
        log.info("向手机{}发送短信验证码{}", target, smsCaptcha.getCode());
        Result<String> result = ResultStatus.SUCCESS.toResult("短信验证码发送成功");
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONUtil.toJsonStr(result));
        } catch (Exception e) {
            log.error("短信验证码发送失败", e);
        }
    }
}
