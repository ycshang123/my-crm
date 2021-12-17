package top.ycshang.crm.security.auth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import top.ycshang.crm.common.vo.SystemResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: my-crm
 * @description: 暂无权限处理类
 * @author: ycshang
 * @create: 2021-12-17 11:42
 **/
@Component
public class SystemAuthAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        SystemResult.createByErrorCodeMessage(403,"无权限");
    }
}