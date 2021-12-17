package top.ycshang.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import top.ycshang.crm.common.entity.SystemRole;
import top.ycshang.crm.common.entity.SystemUser;
import top.ycshang.crm.common.entity.SystemUserRole;
import top.ycshang.crm.common.vo.SystemMenuVo;
import top.ycshang.crm.common.vo.SystemResult;
import top.ycshang.crm.common.vo.SystemUserInfoVo;
import top.ycshang.crm.common.vo.SystemUserVo;
import top.ycshang.crm.dto.PageDto;
import top.ycshang.crm.service.SystemRoleService;
import top.ycshang.crm.service.SystemUserRoleService;
import top.ycshang.crm.service.SystemUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:31
 **/
@Slf4j
@RestController
@RequestMapping("/api/user")
@Api(value = "用户操作接口", tags = {"用户操作接口"})
public class SystemUserController {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private SystemUserRoleService systemUserRoleService;

    @Resource
    private SystemRoleService systemRoleService;

    /**
     * 用户列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "用户列表不分页", httpMethod = "POST", produces = "用户列表不分页")
    public SystemResult<?> getList() {
        List<SystemUser> systemUserList = systemUserService.list();
        return SystemResult.createByCodeSuccess(1, "用户列表查询成功", systemUserList);
    }

    /**
     * page分页，前端vue传值，json格式
     */
    @PostMapping("/page")
    @ApiOperation(value = "用户列表分页查询", httpMethod = "POST", produces = "用户分页")
    public SystemResult<?> page(@RequestBody PageDto pageDto) {
        log.info(String.valueOf(pageDto));
        //获取当前页
        if (pageDto != null) {
            //当前页、每页多少条
            int pageNum = pageDto.getPageNum();
            int pageSize = pageDto.getPageSize();
            //模糊查询的字段，如accountName
            String accountName = pageDto.getKeywords();
            IPage<SystemUser> iPage = new Page<>(pageNum, pageSize);
            IPage<SystemUser> page;
            if (accountName == null || "".equals(accountName)) {
                page = systemUserService.page(iPage);
            } else {
                page = systemUserService.page(iPage, new QueryWrapper<SystemUser>().lambda().like(SystemUser::getAccountName, accountName));
            }
            //查询所有用户的信息 封装在page中
            //查询当前用户的角色的权限菜单
            // 根据userId查询对于的RoleId和Role信息
            List<SystemUser> systemUserList = page.getRecords();
            log.info(String.valueOf(systemUserList));

            List<SystemUser> list = new ArrayList<>();
            for (SystemUser systemUser : systemUserList) {
                //查询roleId
                SystemUserRole userRole = systemUserRoleService.getOne(new QueryWrapper<SystemUserRole>().lambda().eq(SystemUserRole::getUserId, systemUser.getId()));
                //根据roleId查询role角色信息
                SystemRole systemRole = systemRoleService.getById(userRole.getRoleId());
                log.info(String.valueOf(systemRole));
                SystemUserVo systemUserVo = new SystemUserVo();
                BeanUtils.copyProperties(systemUser, systemUserVo);
                systemUserVo.setRoleName(systemRole.getRoleCn());
                systemUserVo.setRoleId(systemRole.getId());
                systemUserVo.setSysRole(systemRole);
                list.add(systemUserVo);
            }
            page.setRecords(list);
            return SystemResult.createByCodeSuccess(1, "执行成功！", page);
        } else {
            return null;
        }
    }

    /**
     * 用户插入
     */
    @PostMapping("/save")
    @ApiOperation(value = "用户插入", httpMethod = "POST", produces = "用户新增")
    public SystemResult<?> saveUserAndRole(@RequestBody SystemUserVo systemUserVo) {
        int i = 0;
        if (systemUserVo != null) {
            i = systemUserService.saveUserAndRole(systemUserVo);
        }
        if (i > 0) {
            return SystemResult.createByCodeSuccess(1, "执行成功！", i);
        } else {
            return SystemResult.createByError();
        }
    }

    /**
     * 用户更新
     */
    @PostMapping("/update")
    @ApiOperation(value = "用户更新", httpMethod = "POST", produces = "用户更新")
    public SystemResult<?> updateUserAndRole(@RequestBody SystemUserVo systemUserVo) {
        int i = 0;
        if (systemUserVo != null) {
            i = systemUserService.updateUserAndRole(systemUserVo);
        }
        if (i > 0) {
            return SystemResult.createByCodeSuccess(1, "执行成功！", i);
        } else {
            return SystemResult.createByError();
        }
    }

    /**
     * 用户删除
     */

    @PostMapping("/delete")
    @ApiOperation(value = "用户删除", httpMethod = "POST", produces = "用户删除")
    public SystemResult<?> delete(@RequestBody SystemUserVo systemUserVo) {
        if (systemUserVo != null) {
            // 删除角色
            SystemUserRole userRole = systemUserRoleService.getOne(new QueryWrapper<SystemUserRole>().lambda().eq(SystemUserRole::getUserId, systemUserVo.getId()));
            systemUserRoleService.removeById(userRole.getId());
            // 删除用户
            boolean removeById = systemUserService.removeById(systemUserVo.getId());
            if (removeById) {
                return SystemResult.createByCodeSuccess(1, "执行成功！", removeById);
            } else {
                return SystemResult.createByError();
            }
        } else {
            return SystemResult.createByError();
        }
    }

    /**
     * 获取当前用户的登录信息Authorization
     */
    @PostMapping("/getInfo")
    @ApiOperation(value = "获取当前登录用户的信息", httpMethod = "POST", produces = "获取当前登录用户的信息")
    public SystemResult<?> getInfo(@RequestHeader(name = "Authorization") String token) {
        // 获取当前登录者的用户名，Info别，电话，角色。。以及对应能访问的菜单，创建时间等
        SystemUserInfoVo systemUserInfoVo = systemUserService.getInfo(token);
        return SystemResult.createByCodeSuccess(1, "执行成功！", systemUserInfoVo);
    }

    /**
     * 获取当前用户的登录权限
     */
    @PostMapping("/permissions")
    @ApiOperation(value = "获取当前登录用户的权限", httpMethod = "POST", produces = "获取当前登录用户的权限")
    public SystemResult<?> getPermissions(@RequestHeader(name = "Authorization") String token) {
        // 获取当前登录者的用户名，密码，性别，电话..... 角色，对应能访问的菜单，创建时间等
        Map<String, List<SystemMenuVo>> map = systemUserService.getPermissions(token);
        List<SystemMenuVo> pList = map.get("sys");
        List<SystemMenuVo> crmList = map.get("crm");
        //为了美观进行封装一个菜单，可以不封装
        List<SystemMenuVo> menuVoList = new ArrayList<>();
        SystemMenuVo menuVo = new SystemMenuVo();
        menuVo.setMenuName("后台系统管理");
        menuVo.setMenuPath("sys");
        menuVo.setParentId("0");
        menuVo.setMenuType("menu");
        menuVo.setMenuComponent("layoutHeaderAside");
        menuVo.setChildren(pList);

        SystemMenuVo menuVo1 = new SystemMenuVo();
        menuVo1.setMenuName("CRM管理");
        menuVo1.setMenuPath("crm");
        menuVo1.setParentId("1");
        menuVo1.setMenuType("menu");
        menuVo1.setMenuComponent("layoutHeaderAside");
        menuVo1.setChildren(crmList);

        menuVoList.add(menuVo);
        menuVoList.add(menuVo1);
        return SystemResult.createByCodeSuccess(1, "执行成功！", menuVoList);
    }
}