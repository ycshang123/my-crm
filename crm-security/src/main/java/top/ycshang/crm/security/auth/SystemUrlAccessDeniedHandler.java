package top.ycshang.crm.security.auth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import top.ycshang.crm.common.util.ResponseUtil;
import top.ycshang.crm.common.vo.SystemResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: my-crm
 * @description: 自定义403权限
 * @author: ycshang
 * @create: 2021-12-17 15:55
 **/
@Component
public class SystemUrlAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResponseUtil.out(response, SystemResult.createByCodeSuccess(403, e.getMessage(), ""));
    }
}