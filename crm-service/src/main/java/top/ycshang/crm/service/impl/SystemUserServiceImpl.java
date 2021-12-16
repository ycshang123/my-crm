package top.ycshang.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.ycshang.crm.common.entity.*;
import top.ycshang.crm.common.vo.MenuVo;
import top.ycshang.crm.common.vo.UserInfoVo;
import top.ycshang.crm.common.vo.UserVo;
import top.ycshang.crm.mapper.*;
import top.ycshang.crm.service.SystemUserService;

import javax.annotation.Resource;
import java.util.*;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 16:34
 **/
@Service
@Transactional(rollbackFor = {RuntimeException.class})
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {
    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private SystemUserRoleMapper systemUserRoleMapper;

    @Resource
    private SystemRoleMapper systemRoleMapper;

    @Resource
    private SystemRoleMenuMapper systemRoleMenuMapper;

    @Resource
    private SystemMenuMapper systemMenuMapper;

    @Override
    public int saveUserAndRole(UserVo systemUserVo) {
        // 插入SystemUser
        SystemUser systemUser = new SystemUser();
        BeanUtils.copyProperties(systemUserVo, systemUser);
        //默认用户是启用的，非禁用
        systemUser.setIsLock("0");
        // 加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwd = systemUserVo.getPassword();
        String pass = bCryptPasswordEncoder.encode(pwd);
        systemUser.setPassword(pass);
        systemUserMapper.insert(systemUser);

        // 插入SystemUserRole
        SystemUserRole systemUserRole = new SystemUserRole();
        systemUserRole.setRoleId(systemUserVo.getRoleId());
        systemUserRole.setUserId(systemUser.getId());
        return systemUserRoleMapper.insert(systemUserRole);
    }

    @Override
    public int updateUserAndRole(UserVo systemUserVo) {
        SystemUser systemUser = new SystemUser();
        BeanUtils.copyProperties(systemUserVo, systemUser);
        systemUserMapper.updateById(systemUser);
        // 更新role角色(只需更新roleId)
        SystemUserRole systemUserRole = systemUserRoleMapper.selectOne(new QueryWrapper<SystemUserRole>().lambda().eq(SystemUserRole::getUserId, systemUserVo.getId()));
        systemUserRole.setRoleId(systemUserVo.getRoleId());
        return systemUserRoleMapper.updateById(systemUserRole);
    }

    @Override
    public UserInfoVo getInfo(String token) {
        //获取登录者的用户信息
        SystemUser systemUser = systemUserMapper.selectOne(new QueryWrapper<SystemUser>().lambda().eq(SystemUser::getLoginToken, token));
        // 复制到Vo中进行管理
        UserInfoVo systemUserInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(systemUser, systemUserInfoVo);
        System.out.println("111111---------》》" + systemUserInfoVo);
        // 查询登录用户的角色
        SystemUserRole userRole = systemUserRoleMapper.selectOne(new QueryWrapper<SystemUserRole>().lambda().eq(SystemUserRole::getUserId, systemUser.getId()));
        List<SystemRole> systemRoleList = systemRoleMapper.selectList(new QueryWrapper<SystemRole>().lambda().eq(SystemRole::getId, userRole.getRoleId()));
        System.out.println("111111---------》》" + systemRoleList);
        Set<String> roles = new HashSet<>();
        // 如果有多个角色可以扩展，此处用第一个角色演示
        if (systemRoleList != null && systemRoleList.size() > 0) {
            roles.add(systemRoleList.get(0).getRoleEn());
            // 获取当前角色对应的有权限的菜单列表
            List<SystemRoleMenu> systemRoleMenuList = systemRoleMenuMapper.selectList(new QueryWrapper<SystemRoleMenu>().lambda().eq(SystemRoleMenu::getRoleId, systemRoleList.get(0).getId()));
            String rId = systemRoleList.get(0).getId();
            //获取所有菜单
            Set<String> getAllMenus = systemMenuMapper.getAllMenus(rId);
            // 获取所有的授权
            Set<String> getPermission = systemMenuMapper.getPermission(rId);
            //vue前台处理
            systemUserInfoVo.setMenuList(getAllMenus);
            systemUserInfoVo.setPermissionList(getPermission);
        }
        System.out.println("111111---------》》" + systemUserInfoVo);
        return systemUserInfoVo;
    }

    @Override
    public Map<String, List<MenuVo>> getPermissions(String token) {
        List<MenuVo> list = new ArrayList<>();
        List<MenuVo> newList = new ArrayList<>();
        List<MenuVo> crmList = new ArrayList<>();
        Map<String, List<MenuVo>> map = new HashMap<>();
        //获取登录者的用户信息
        SystemUser systemUser = new SystemUser();
        systemUser = systemUserMapper.selectOne(new QueryWrapper<SystemUser>().lambda().eq(SystemUser::getLoginToken, token));
        // 复制到Vo中进行管理
        UserInfoVo systemUserInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(systemUser, systemUserInfoVo);
        // 查询登录用户的角色
        SystemUserRole userRole = systemUserRoleMapper.selectOne(new QueryWrapper<SystemUserRole>().lambda().eq(SystemUserRole::getUserId, systemUser.getId()));
        List<SystemRole> systemRoleList = systemRoleMapper.selectList(new QueryWrapper<SystemRole>().lambda().eq(SystemRole::getId, userRole.getRoleId()));
        Set<String> roles = new HashSet<>();
        // 如果有多个角色可以扩展，此处用第一个角色演示
        if (systemRoleList != null && systemRoleList.size() > 0) {
            roles.add(systemRoleList.get(0).getRoleEn());
            // 获取当前角色对应的有权限的菜单列表
            List<SystemRoleMenu> systemRoleMenuList = systemRoleMenuMapper.selectList(new QueryWrapper<SystemRoleMenu>().lambda().eq(SystemRoleMenu::getRoleId, systemRoleList.get(0).getId()));

            for (SystemRoleMenu systemRoleMenu : systemRoleMenuList) {
                SystemMenu systemMenu = systemMenuMapper.selectById(systemRoleMenu.getMenuId());
                MenuVo menuVo = new MenuVo();
                BeanUtils.copyProperties(systemMenu, menuVo);
                list.add(menuVo);
            }

        }
        //查找根节点
        List<MenuVo> root = systemMenuMapper.getMenuListById("0");
        for (MenuVo menuVo : root) {
            List<MenuVo> sub = new ArrayList<>();
            for (MenuVo mv : root) {
                if (mv.getParentId().equals(mv.getId())) {
                    sub.add(menuVo);
                }
            }
            menuVo.setChildren(sub);
            newList.add(menuVo);
        }
        map.put("sys", newList);

        //查找根节点
        List<MenuVo> root1 = systemMenuMapper.getMenuListById("1");
        for (MenuVo menuVo : root1) {
            List<MenuVo> sub1 = new ArrayList<>();
            for (MenuVo smv : list) {
                if (menuVo.getParentId().equals(menuVo.getId())) {
                    sub1.add(menuVo);
                }
            }
            menuVo.setChildren(sub1);
            crmList.add(menuVo);
        }
        map.put("crm", crmList);
        return map;
    }

}