package top.ycshang.crm.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import top.ycshang.crm.common.vo.SystemResult;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:48
 **/
@Slf4j
@Component
public class SystemAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authenticationException) throws IOException, ServletException {
        SystemResult<Object> message;

        if (authenticationException instanceof UsernameNotFoundException || authenticationException instanceof BadCredentialsException) {
            message = SystemResult.createByErrorMessage(authenticationException.getMessage());
        } else if (authenticationException instanceof CredentialsExpiredException) {
            message = SystemResult.createByErrorMessage("CredentialsExpiredException,cert证书验证异常");
        } else if (authenticationException instanceof AccountExpiredException) {
            message = SystemResult.createByErrorMessage("LockedException,账号已经Lock!");
        } else {
            message = SystemResult.createByErrorMessage("IOException,登陆异常,系统错误!");
        }
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(message));
    }
}