package top.ycshang.crm.security.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import top.ycshang.crm.common.entity.SystemUser;
import top.ycshang.crm.common.util.ResponseUtil;
import top.ycshang.crm.common.vo.SecurityUser;
import top.ycshang.crm.common.vo.SystemResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: my-crm
 * @description: 认证处理
 * @author: ycshang
 * @create: 2021-12-17 15:27
 **/
@Component
public class SystemAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        SystemUser user = new SystemUser();
        SecurityUser securityUser = ((SecurityUser) auth.getPrincipal());
        user.setLoginToken(securityUser.getLoginUser().getLoginToken());
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        map.put("loginToken", securityUser.getLoginUser().getLoginToken());
        ResponseUtil.out(response, SystemResult.createByCodeSuccess(200, "登录成功", map));
    }
}