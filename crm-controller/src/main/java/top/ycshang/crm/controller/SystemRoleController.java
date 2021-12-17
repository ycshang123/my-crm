package top.ycshang.crm.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.ycshang.crm.common.entity.SystemRole;
import top.ycshang.crm.common.vo.SystemMenuVo;
import top.ycshang.crm.common.vo.SystemResult;
import top.ycshang.crm.common.vo.SystemRoleVo;
import top.ycshang.crm.dto.PageDto;
import top.ycshang.crm.service.SystemMenuService;
import top.ycshang.crm.service.SystemRoleService;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:31
 **/
@RestController
@RequestMapping("/api/role")
@Api(produces = "系统角色接口", tags = {"系统角色接口"})
public class SystemRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SystemRoleService systemRoleService;

    @Resource
    private SystemMenuService systemMenuService;

    /**
     * 不分页查询所有数据： 获取角色里面有多少个用户是该角色   admin  3人（张三，李四，王五）
     *
     * @return 所有数据
     */
    @PostMapping(value = "/list")
    @ApiOperation(value = "角色列表不分页", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> list() {
        Map<String, List<SystemRoleVo>> map = systemRoleService.getRoleAndUserList();
        return SystemResult.createBySuccess("查询成功！", map);
    }

    @PostMapping(value = "/page")
    @ApiOperation(value = "角色列表分页", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> page(@RequestBody PageDto pageDto) {
        if (pageDto != null) {
            //每页多少条
            int pageNum = pageDto.getPageNum();
            int pageSize = pageDto.getPageSize();
            Map<String, Object> map = systemRoleService.getRoleAndUserByPage(pageNum, pageSize);
            return SystemResult.createBySuccess("查询成功！", map);
        } else {
            return SystemResult.createByError();
        }
    }

    /**
     * 查询不分页数据
     */
    @PostMapping(value = "/commonList")
    @ApiOperation(value = "角色实体列表", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> getList() {
        List<SystemRole> list = systemRoleService.list();
        return SystemResult.createByCodeSuccess(1, "查询成功", list);
    }


    @PostMapping(value = "/getAllMenus")
    @ApiOperation(value = "角色列表菜单", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> getAllMenus() {
        List<SystemMenuVo> menuVoList = systemMenuService.getAllMenusByElTree();
        return SystemResult.createByCodeSuccess(1, "查询成功", menuVoList);
    }

    @PostMapping(value = "/getPermissionById")
    @ApiOperation(value = "角色菜单", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> getTree(@RequestParam(value = "roleId") String roleId) {
        if (roleId != null) {
            SystemRoleVo systemRoleVo = systemMenuService.getTree(roleId);
            return SystemResult.createByCodeSuccess(1, "查询成功", systemRoleVo.getCheckedIds());
        }
        return SystemResult.createByCodeSuccess(1, "查询成功", null);
    }


    /**
     * 分配菜单权限
     */
    @PostMapping("/setMenus")
    @ApiOperation(value = "设置系统管理角色菜单", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> setMenus(@RequestBody JSONObject jsonObject) {
        int i = systemRoleService.setMenus(jsonObject);
        if (i > 0) {
            return SystemResult.createBySuccess("执行成功！", i);
        }
        return SystemResult.createByError();
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("{id}")
    @ApiOperation(value = "获取角色信息", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> selectOne(@PathVariable Serializable id) {
        return SystemResult.createBySuccess(this.systemRoleService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping(value = "/save")
    @ApiOperation(value = "保存角色", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> insert(@RequestBody SystemRole systemRole) {
        if (systemRole != null) {
            //// 角色的名称
            //String roleName = jsonObject.getString("roleCn");
            //// 0 启动 1 禁用
            //String isEnable = jsonObject.getString("isEnable");
            ////角色的描述
            //String describeText = jsonObject.getString("describeText");
            ////角色的描述 添加的字段
            //String roleEn = jsonObject.getString("roleEn");
            //
            //SystemRole systemRole = new SystemRole();
            //systemRole.setRoleCn(roleName);
            //systemRole.setDescribeText(describeText);
            //systemRole.setRoleEn(roleEn);
            //if (isEnable != null && !"".equals(isEnable)) {
            //    if ("true".equals(isEnable)) {
            //        //表示启用
            //        systemRole.setIsEnable("0");
            //    }
            //} else {
            //    //表示禁用
            //    systemRole.setIsEnable("1");
            //}
            boolean saveOrUpdate = systemRoleService.saveOrUpdate(systemRole);
            return SystemResult.createBySuccess("执行成功！", saveOrUpdate);
        }
        return SystemResult.createByError();
    }

    @PostMapping(value = "/update")
    @ApiOperation(value = "更新角色", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> update(@RequestBody SystemRole systemRole) {
        if (systemRole != null) {
            //String roleName = jsonObject.getString("roleCn");
            //String id = jsonObject.getString("id");
            //String isEnable = jsonObject.getString("isEnable");
            //String describeText = jsonObject.getString("describeText");
            //SystemRole systemRole = new SystemRole();
            //systemRole.setId(id);
            //systemRole.setRoleCn(roleName);
            //systemRole.setDescribeText(describeText);
            //if (isEnable != null && !"".equals(isEnable)) {
            //    if ("true".equals(isEnable)) {
            //        systemRole.setIsEnable("0");//启用
            //    } else {
            //        systemRole.setIsEnable("1");//禁用
            //    }
            //}
            boolean saveOrUpdate = systemRoleService.saveOrUpdate(systemRole);
            return SystemResult.createBySuccess("执行成功！", saveOrUpdate);
        }
        return SystemResult.createByError();
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除角色 ", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> delete(@RequestParam(value = "id") String id) {
        if (id != null) {
            return SystemResult.createBySuccess(this.systemRoleService.removeById(id));
        } else {
            return SystemResult.createByError();
        }
    }
}