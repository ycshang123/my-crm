package top.ycshang.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.ycshang.crm.common.entity.SystemMenu;
import top.ycshang.crm.common.entity.SystemUserRole;
import top.ycshang.crm.common.vo.*;
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
    public List<SystemMenuVo> getAllMenusByElTree() {
        //查询根节点
        List<SystemMenuVo> sysMenusVoList = systemMenuMapper.getMenuListByTree();
        List<SystemMenuVo> sysMenusVoList1 = new ArrayList<>();
        for (SystemMenuVo sysMenusVo : sysMenusVoList) {
            List<SystemMenuVo> permissionList = systemMenuMapper.getMenuListById(sysMenusVo.getId());

            sysMenusVo.setChildren(permissionList);
            sysMenusVoList1.add(sysMenusVo);
        }
        return sysMenusVoList1;
    }

    /**
     * 菜单的树形结构
     *
     * @return map
     */
    @Override
    public Map<String, List<SystemMenuVo>> getMenusList() {
        //查询所有根节点下面的第一层次节点 ,0 和 1 都展示
        List<SystemMenuVo> sysMenusList = systemMenuMapper.getMenuListByTree();
        // 查询所有记录
        List<SystemMenuVo> menusVoList = systemMenuMapper.getList();
        for (SystemMenuVo sysMenusVo : sysMenusList) {
            List<SystemMenuVo> children = new ArrayList<>();
            for (SystemMenuVo sysMenus : menusVoList) {
                if (sysMenus.getParentId().equals(sysMenusVo.getId())) {
                    children.add(sysMenus);
                }
            }
            sysMenusVo.setChildren(children);
        }
        Map<String, List<SystemMenuVo>> map = new HashMap<>(4);
        map.put("menuTree", sysMenusList);
        return map;
    }

    @Override
    public SystemRoleVo getTree(String roleId) {
        List<SystemUserRole> cloudUserRoleList = systemUserRoleMapper.selectList(new QueryWrapper<SystemUserRole>().lambda().eq(SystemUserRole::getRoleId, roleId));
        SystemRoleVo systemRoleVo = new SystemRoleVo();
        //菜单获取根节点   为了前端vue 复选框 ☒  √ 做准备 checkIdsList
        List<SystemMenuVo> menusList = systemMenuMapper.getMenuListById("0");
        List<String> checkIdsList = new ArrayList<>();
        List<SystemMenuVo> menusVoList = new ArrayList<>();
        for (SystemMenuVo sysMenusVo : menusList) {
            //获取权限和前端页面的 复选框回显做准备
            List<SystemMenuVo> menuList = systemMenuMapper.getMenuList(sysMenusVo.getId(), roleId);
            if (menuList != null) {
                for (SystemMenu systemMenu : menuList) {
                    checkIdsList.add(systemMenu.getId());
                }
            }
            sysMenusVo.setMenuList(menuList);
            menusVoList.add(sysMenusVo);
        }
        systemRoleVo.setCheckedIds(checkIdsList);
        systemRoleVo.setSysMenuVo(menusVoList);

        return systemRoleVo;

    }

    @Override
    public List<DictType> getDict() {
        return systemMenuMapper.getDict();
    }
}