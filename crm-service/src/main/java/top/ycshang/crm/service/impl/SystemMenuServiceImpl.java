package top.ycshang.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.ycshang.crm.common.entity.SystemMenu;
import top.ycshang.crm.common.entity.SystemUserRole;
import top.ycshang.crm.common.vo.DictType;
import top.ycshang.crm.common.vo.MenuVo;
import top.ycshang.crm.common.vo.RoleVo;
import top.ycshang.crm.mapper.SystemMenuMapper;
import top.ycshang.crm.mapper.SystemUserRoleMapper;
import top.ycshang.crm.service.SystemMenuService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 16:31
 **/
@Service
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenu> implements SystemMenuService {
    @Resource
    private SystemMenuMapper systemMenuMapper;

    @Resource
    private SystemUserRoleMapper systemUserRoleMapper;

    @Override
    public List<MenuVo> getAllMenusByElTree() {
        //查询根节点
        List<MenuVo> menuVoList = systemMenuMapper.getMenuListByTree();
        List<MenuVo> res = new ArrayList<>();
        for (MenuVo mv : menuVoList) {
            List<MenuVo> permissionList = systemMenuMapper.getMenuListById(mv.getId());
            mv.setChildren(permissionList);
            res.add(mv);
        }
        return res;
    }

    @Override
    public Map<String, List<MenuVo>> getMenusList() {
        //查询所有根节点下面的第一层次节点 ,0 和 1 都展示
        List<MenuVo> menuVoList = systemMenuMapper.getMenuListByTree();
        // 查询所有记录
        List<MenuVo> menusVoList = systemMenuMapper.getList();
        for (MenuVo mv : menuVoList) {
            List<MenuVo> children = new ArrayList<>();
            for (MenuVo menuVo : menusVoList) {
                if (menuVo.getParentId().equals(mv.getId())) {
                    children.add(menuVo);
                }
            }
            mv.setChildren(children);
        }
        Map<String, List<MenuVo>> map = new HashMap<>(4);
        map.put("menuTree", menuVoList);
        return map;
    }

    @Override
    public RoleVo getTree(String roleId) {
        List<SystemUserRole> systemUserRoleList = systemUserRoleMapper.selectList(new QueryWrapper<SystemUserRole>().lambda().eq(SystemUserRole::getRoleId, roleId));
        RoleVo roleVo = new RoleVo();
        //菜单获取根节点   为了前端vue复选框做准备 checkIdsList
        List<MenuVo> menuVoList = systemMenuMapper.getMenuListById("0");
        List<String> checkIdsList = new ArrayList<>();
        List<MenuVo> res = new ArrayList<>();
        for (MenuVo menuVo : menuVoList) {
            //获取权限和前端页面的 复选框回显做准备
            List<MenuVo> menuList = systemMenuMapper.getMenuList(menuVo.getId(), roleId);
            if (menuList != null) {
                for (MenuVo mv : menuList) {
                    checkIdsList.add(mv.getId());
                }
            }
            menuVo.setMenuList(menuList);
            res.add(menuVo);
        }
        roleVo.setCheckedIds(checkIdsList);
        roleVo.setMenuVos(res);
        return roleVo;
    }

    @Override
    public List<DictType> getDict() {
        return systemMenuMapper.getDict();
    }
}