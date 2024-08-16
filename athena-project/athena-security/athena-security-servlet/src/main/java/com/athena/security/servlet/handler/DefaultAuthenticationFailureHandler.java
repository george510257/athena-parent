package com.athena.security.servlet.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.athena.common.bean.result.Result;
import com.athena.common.bean.result.ResultStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = null;
        if (exception instanceof OAuth2AuthenticationException oauth2AuthenticationException) {
            message = oauth2AuthenticationException.getError().getDescription();
        }
        if (StrUtil.isBlank(message)) {
            message = exception.getMessage();
        }
        Result<String> result = ResultStatus.PARAM_ERROR.toResult(message);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }
}
