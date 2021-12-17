package top.ycshang.crm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.ycshang.crm.common.entity.*;
import top.ycshang.crm.common.vo.MenuVo;
import top.ycshang.crm.common.vo.RoleVo;
import top.ycshang.crm.common.vo.SystemMenuVo;
import top.ycshang.crm.common.vo.SystemRoleVo;
import top.ycshang.crm.mapper.*;
import top.ycshang.crm.service.SystemRoleService;

import javax.annotation.Resource;
import java.util.*;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 16:33
 **/
@Service
@Transactional(rollbackFor = {RuntimeException.class})
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRole> implements SystemRoleService {

    @Resource
    private SystemRoleMapper systemRoleMapper;

    @Resource
    private SystemMenuMapper systemMenuMapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private SystemUserRoleMapper systemUserRoleMapper;

    @Resource
    private SystemRoleMenuMapper systemRoleMenuMapper;

    /**
     * 查询用户和角色的接口
     *
     * @return
     */
    @Override
    public Map<String, List<SystemRoleVo>> getRoleAndUserList() {
        //查询所有role的数据
        List<SystemRoleVo> sysRoleVoList = systemRoleMapper.getList();
        for (SystemRoleVo sysRoleVo : sysRoleVoList) {
            //获取该权限的所有用户
            String roleId = sysRoleVo.getId();
            List<SystemUserRole> sysUserRoleList = systemUserRoleMapper.selectList(new QueryWrapper<SystemUserRole>().lambda().eq(SystemUserRole::getRoleId, roleId));
            List<SystemUser> systemUserList = new ArrayList<>();
            for (SystemUserRole sur : sysUserRoleList) {
                SystemUser systemUser = systemUserMapper.selectById(sur.getUserId());
                systemUserList.add(systemUser);
            }
            sysRoleVo.setSystemUsers(systemUserList);
            //菜单获取根节点   为了前端vue 复选框 ☒  √ 做准备 checkIdsList
            List<SystemMenuVo> menusList = systemMenuMapper.getMenuListById("0");
            List<String> checkIdsList = new ArrayList<>();
            List<SystemMenuVo> menusVoList = new ArrayList<>();
            for (SystemMenuVo sysMenusVo : menusList) {
                //获取权限和前端页面的 复选框回显做准备
                List<SystemMenuVo> menuList = systemMenuMapper.getMenuList(sysMenusVo.getId(), sysRoleVo.getId());
                if (menuList != null) {
                    for (SystemMenu sysMenus : menuList) {
                        checkIdsList.add(sysMenus.getId());
                    }
                }
                sysMenusVo.setMenuList(menuList);
                menusVoList.add(sysMenusVo);
            }
            sysRoleVo.setCheckedIds(checkIdsList);
            sysRoleVo.setSysMenuVo(menusVoList);
        }
        Map<String, List<SystemRoleVo>> map = new HashMap<>(4);
        map.put("list", sysRoleVoList);
        return map;
    }

    /**
     * 查询用户和角色的接口
     *
     * @return map
     */
    @Override
    public Map<String, Object> getRoleAndUserByPage(int pageNum, int pageRow) {
        //查询所有role的数据
        IPage<SystemRole> iPage = new Page<>(pageNum, pageRow);
        IPage<SystemRole> selectPage = systemRoleMapper.selectPage(iPage, null);
        List<SystemRoleVo> sysRoleVoList = new ArrayList<>();
        List<SystemRole> sysRoleList = selectPage.getRecords();
        for (SystemRole sysRole : sysRoleList) {
            SystemRoleVo systemRoleVo = new SystemRoleVo();
            //获取该权限的所有用户
            String roleId = sysRole.getId();
            List<SystemUserRole> systemUserRoleList = systemUserRoleMapper.selectList(new QueryWrapper<SystemUserRole>().lambda().eq(SystemUserRole::getRoleId, roleId));
            List<SystemUser> systemUserList = new ArrayList<>();
            for (SystemUserRole systemUserRole : systemUserRoleList) {
                SystemUser systemUser = systemUserMapper.selectById(systemUserRole.getUserId());
                systemUserList.add(systemUser);
            }
            systemRoleVo.setSystemUsers(systemUserList);

            //菜单获取根节点   为了前端vue 复选框 ☒  √ 做准备
            List<SystemMenuVo> menusList = systemMenuMapper.getMenuListById("0");
            List<String> checkIdsList = new ArrayList<>();
            List<SystemMenuVo> menusVoList = new ArrayList<>();
            for (SystemMenuVo sysMenusVo : menusList) {
                //获取权限和前端页面的 复选框回显做准备
                List<SystemMenuVo> menuList = systemMenuMapper.getMenuList(sysMenusVo.getId(), sysRole.getId());
                if (menuList != null) {
                    for (SystemMenu sysMenu : menuList) {
                        checkIdsList.add(sysMenu.getId());
                    }
                }
                sysMenusVo.setMenuList(menuList);
                menusVoList.add(sysMenusVo);
            }
            systemRoleVo.setCheckedIds(checkIdsList);
            systemRoleVo.setSysMenuVo(menusVoList);
            sysRoleVoList.add(systemRoleVo);
        }
        Map<String, Object> map = new HashMap<>(4);
        map.put("list", sysRoleVoList);
        map.put("page", selectPage.getRecords());
        map.put("current", selectPage.getCurrent());
        map.put("size", selectPage.getSize());
        map.put("total", selectPage.getTotal());
        map.put("records", selectPage.getRecords());
        return map;
    }

    /**
     * 设置菜单权限，尤其是获取id
     *
     * @param jsonObject jsonObject
     * @return int
     */
    @Override
    public int setMenus(JSONObject jsonObject) {
        int count = 0;
        //vue-> "[100,101,102]"
        String ids = jsonObject.getString("ids");
        Long roleId = jsonObject.getLong("roleId");

        String[] checkIds = ids.substring(1, ids.length() - 1).split(",");
        //为了保证选中的一致性
        systemRoleMenuMapper.delete(new QueryWrapper<SystemRoleMenu>().lambda().eq(SystemRoleMenu::getRoleId, roleId));
        for (String id : checkIds) {
            if (id != null && !"".equals(id)) {
                Long checkId = Long.valueOf(id.trim());
                SystemRoleMenu sysRoleMenus = new SystemRoleMenu();
                sysRoleMenus.setRoleId(roleId);
                sysRoleMenus.setMenuId(checkId);
                int i = systemRoleMenuMapper.insert(sysRoleMenus);
                count = count + i;
            }
        }
        return count;
    }
}