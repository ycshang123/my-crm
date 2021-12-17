package top.ycshang.crm.security.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.ycshang.crm.common.util.ResponseUtil;
import top.ycshang.crm.common.vo.SystemResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:44
 **/
@Slf4j
@Component
public class SystemAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        if (authenticationException != null) {
            ResponseUtil.out(response, SystemResult.createByErrorCodeMessage(401, authenticationException.getMessage()));
        } else {
            ResponseUtil.out(response, SystemResult.createByErrorCodeMessage(-1, "token无效或者格式不对"));
        }
    }
}