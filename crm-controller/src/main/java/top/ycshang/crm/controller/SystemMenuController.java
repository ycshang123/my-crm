package top.ycshang.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.ycshang.crm.common.entity.SystemMenu;
import top.ycshang.crm.common.vo.DictType;
import top.ycshang.crm.common.vo.SystemMenuVo;
import top.ycshang.crm.common.vo.SystemResult;
import top.ycshang.crm.service.SystemMenuService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:31
 **/
@RestController
@RequestMapping("/api/menus")
@Api(produces = "系统菜单接口", tags = {"系统菜单接口"})
public class SystemMenuController {
    @Resource
    private SystemMenuService systemMenuService;


    @PostMapping(value = "/list")
    @ApiOperation(value = "获取菜单树", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> list() {
        Map<String, List<SystemMenuVo>> map = systemMenuService.getMenusList();
        return SystemResult.createBySuccess("执行成功！", map);
    }

    /**
     * 新增菜单
     *
     * @param systemMenu 菜单实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/save")
    @ApiOperation(value = "新增菜单 ", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> insert(@RequestBody SystemMenu systemMenu) {
        return SystemResult.createBySuccess(this.systemMenuService.save(systemMenu));
    }

    /**
     * 修改菜单
     *
     * @param systemMenu 菜单实体对象
     * @return 修改结果
     */
    @PostMapping(value = "/update")
    @ApiOperation(value = "修改菜单 ", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> update(@RequestBody SystemMenu systemMenu) {
        return SystemResult.createBySuccess(this.systemMenuService.updateById(systemMenu));
    }

    /**
     * 批量删除菜单
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @PostMapping(value = "/batchDelete")
    @ApiOperation(value = "删除菜单", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> batchDelete(@RequestParam("idList") List<Long> idList) {
        for (Long id : idList) {
            // 如果该菜单下存在子菜单，提示先删除子菜单
            List<SystemMenu> menuList = systemMenuService.list(new QueryWrapper<SystemMenu>().eq("parent_id", id));
            if (!CollectionUtils.isEmpty(menuList)) {
                return SystemResult.createByErrorMessage("请先删除子菜单！再重试！");
            }
            systemMenuService.removeById(id);
        }
        return SystemResult.createByCodeSuccess(1, "删除成功", true);
    }

    /**
     * 删除菜单
     *
     * @return 删除结果
     */
    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除菜单", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> delete(@RequestParam("id") String id) {
        if (id != null) {
            // 如果该菜单下存在子菜单，提示先删除子菜单
            List<SystemMenu> menuList = systemMenuService.list(new QueryWrapper<SystemMenu>().eq("parent_id", id));
            if (!CollectionUtils.isEmpty(menuList)) {
                return SystemResult.createByErrorMessage("请先删除子菜单！再重试！");
            }
            return SystemResult.createByCodeSuccess(1, "删除成功", true);
        }
        return SystemResult.createByError();
    }

    /**
     * 获取新增字典
     */
    @PostMapping(value = "/getDict")
    @ApiOperation(value = "获取新增字典", httpMethod = "POST", response = SystemResult.class)
    public SystemResult<?> getDict() {
        List<DictType> list = systemMenuService.getDict();
        return SystemResult.createByCodeSuccess(1, "成功", list);
    }
}