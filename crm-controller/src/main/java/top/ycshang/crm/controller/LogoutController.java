package top.ycshang.crm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ycshang.crm.common.vo.SystemResult;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:30
 **/
@RestController
@Api(produces = "登录和登出", tags = {"登录登出接口"})
public class LogoutController {
    /**
     * logout
     *
     * @return SystemResult
     */
    @PostMapping(value = "/logout")
    @ApiOperation(value = "登出系统", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> logout() {
        return SystemResult.createBySuccess("登出系统成功", null);
    }

    /**
     * login
     *
     * @return SystemResult
     */
    @PostMapping(value = "/login")
    @ApiOperation(value = "登录系统", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> login() {
        return SystemResult.createBySuccess("登录系统成功", null);
    }

}