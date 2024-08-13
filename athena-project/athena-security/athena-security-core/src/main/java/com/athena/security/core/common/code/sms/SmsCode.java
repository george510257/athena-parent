package com.athena.security.core.common.code.sms;

import com.athena.security.core.common.code.base.VerificationCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信验证码
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmsCode extends VerificationCode {
}
