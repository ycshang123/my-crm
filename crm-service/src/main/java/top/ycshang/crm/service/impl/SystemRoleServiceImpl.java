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
@Service("systemRoleService")
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
     * @return Map
     */
    @Override
    public Map<String, List<MenuVo>> getRoleAndUserList() {
        //查询所有role的数据
        List<RoleVo> systemRoleVoList = systemRoleMapper.getList();
        for (RoleVo systemRoleVo : systemRoleVoList) {
            //获取该权限的所有用户
            String roleId = systemRoleVo.getId();
            List<SystemUserRole> systemUserRoleList = systemUserRoleMapper.selectList(new QueryWrapper<SystemUserRole>().lambda().eq(SystemUserRole::getRoleId, roleId));
            List<SystemUser> systemUserList = new ArrayList<>();
            for (SystemUserRole systemUserRole : systemUserRoleList) {
                SystemUser systemUser = systemUserMapper.selectById(systemUserRole.getUserId());
                systemUserList.add(systemUser);
            }
            systemRoleVo.setSystemUsers(systemUserList);

            //菜单获取根节点
            List<MenuVo> menuVoList = systemMenuMapper.getMenuListById("0");
            List<String> checkIdsList = new ArrayList<>();
            List<MenuVo> list = new ArrayList<>();
            for (MenuVo menuVo : menuVoList) {
                //获取权限和前端页面的 复选框回显做准备
                List<MenuVo> menuList = systemMenuMapper.getMenuList(menuVo.getId(), systemRoleVo.getId());
                if (menuList != null) {
                    for (SystemMenu systemMenu : menuList) {
                        checkIdsList.add(systemMenu.getId());
                    }
                }
                menuVo.setMenuList(menuList);
                list.add(menuVo);
            }
            systemRoleVo.setCheckedIds(checkIdsList);
            systemRoleVo.setSystemUsers(systemUserList);
        }
        //map.put("list", list);
        return new HashMap<>(4);
    }

    /**
     * 查询用户和角色的接口
     *
     * @return Map
     */
    @Override
    public Map<String, Object> getRoleAndUserByPage(int pageNum, int pageRow) {
        //查询所有role的数据
        IPage<SystemRole> iPage = new Page<>(pageNum, pageRow);
        IPage<SystemRole> selectPage = systemRoleMapper.selectPage(iPage, null);
        List<RoleVo> systemRoleVoList = new ArrayList<>();
        List<SystemRole> systemRoleList = selectPage.getRecords();
        Iterator<SystemRole> iterator = systemRoleList.iterator();
        while (iterator.hasNext()) {
            RoleVo systemRoleVo = new RoleVo();
            //获取该权限的所有用户
            SystemRole systemRole = iterator.next();
            List<SystemUserRole> systemUserRoleList = systemUserRoleMapper.selectList(new QueryWrapper<SystemUserRole>().lambda().eq(SystemUserRole::getRoleId, systemRole.getId()));
            List<SystemUser> systemUserList = new ArrayList<>();
            for (SystemUserRole systemUserRole : systemUserRoleList) {
                SystemUser systemUser = systemUserMapper.selectById(systemUserRole.getUserId());
                systemUserList.add(systemUser);
            }
            systemRoleVo.setSystemUsers(systemUserList);

            //菜单获取根节点
            List<MenuVo> menusList = systemMenuMapper.getMenuListById("0");
            List<String> checkIdsList = new ArrayList<>();
            List<MenuVo> menusVoList = new ArrayList<>();

            for (MenuVo menuVo : menusList) {
                //获取权限和前端页面的 复选框回显做准备
                List<MenuVo> menuList = systemMenuMapper.getMenuList(menuVo.getId(), systemRole.getId());
                if (menuList != null) {
                    for (SystemMenu systemMenu : menuList) {
                        checkIdsList.add(systemMenu.getId());
                    }
                }
                menuVo.setMenuList(menuList);
                menusVoList.add(menuVo);
            }
            systemRoleVo.setCheckedIds(checkIdsList);
            systemRoleVo.setMenuVos(menusVoList);
            systemRoleList.add(systemRoleVo);
        }
        Map<String, Object> map = new HashMap<>(8);
        map.put("list", systemRoleVoList);
        map.put("page", selectPage.getRecords());
        map.put("current", selectPage.getCurrent());
        map.put("size", selectPage.getSize());
        map.put("total", selectPage.getTotal());
        map.put("records", selectPage.getRecords());
        return map;
    }

    /**
     * 设置菜单权限
     *
     * @param jsonObject json对象
     * @return int
     */
    @Override
    public int setMenus(JSONObject jsonObject) {
        int count = 0;
        //vue传值："[100,101,102]"
        String ids = jsonObject.getString("ids");
        Long roleId = jsonObject.getLong("roleId");
        String[] checkIds = ids.substring(1, ids.length() - 1).split(",");
        //为了保证选中的一致性
        systemRoleMenuMapper.delete(new QueryWrapper<SystemRoleMenu>().lambda().eq(SystemRoleMenu::getRoleId, roleId));
        for (String id : checkIds) {
            if (id != null && !"".equals(id)) {
                Long checkId = Long.valueOf(id.trim());
                SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
                systemRoleMenu.setRoleId(roleId);
                systemRoleMenu.setMenuId(checkId);
                int i = systemRoleMenuMapper.insert(systemRoleMenu);
                count = count + i;
            }
        }
        return count;
    }
}