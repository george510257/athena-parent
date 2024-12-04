package com.gls.athena.security.servlet.captcha.sms;

import com.gls.athena.security.servlet.captcha.base.BaseCaptcha;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信验证码
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmsCaptcha extends BaseCaptcha {
}
