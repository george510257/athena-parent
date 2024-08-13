package com.athena.security.core.common.code;

import com.athena.security.core.common.code.base.VerificationCodeProvider;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Component
public class VerificationCodeManager {

    @Resource
    private List<VerificationCodeProvider<?, ?, ?>> providers;

    public VerificationCodeProvider<?, ?, ?> getProvider(ServletWebRequest request) {
        for (VerificationCodeProvider<?, ?, ?> provider : providers) {
            if (provider.support(request)) {
                return provider;
            }
        }
        return null;
    }

}
