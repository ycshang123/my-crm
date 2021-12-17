package top.ycshang.crm.security.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import top.ycshang.crm.common.vo.SystemResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: my-crm
 * @description: 登出成功
 * @author: ycshang
 * @create: 2021-12-17 11:58
 **/
@Component
public class SystemAuthenticationLogoutHandler implements LogoutSuccessHandler {
    /**
     * 用户登出返回结果
     * 这里应该让前端清除掉Token
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("msg", "登出成功");
        SecurityContextHolder.clearContext();
        SystemResult.createBySuccess(result);
    }
}