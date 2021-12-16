package top.ycshang.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.ycshang.crm.common.entity.SystemRole;
import top.ycshang.crm.common.entity.SystemUser;
import top.ycshang.crm.common.entity.SystemUserRole;
import top.ycshang.crm.common.vo.SecurityUser;
import top.ycshang.crm.mapper.SystemRoleMapper;
import top.ycshang.crm.mapper.SystemUserMapper;
import top.ycshang.crm.mapper.SystemUserRoleMapper;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 16:34
 **/
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private SystemRoleMapper systemRoleMapper;

    @Resource
    private SystemUserRoleMapper systemUserRoleMapper;

    /***
     * 根据账号获取用户信息
     * @param accountName 账号
     * @return UserDetails 用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        List<SystemUser> userList = systemUserMapper.selectList(new QueryWrapper<SystemUser>().eq("account_name", accountName));
        SystemUser user;
        // 判断用户是否存在
        if (!CollectionUtils.isEmpty(userList)) {
            user = userList.get(0);
        } else {
            throw new UsernameNotFoundException("用户名数据库未查到，请注册后再试！");
        }
        // 返回UserDetails实现类
        return new SecurityUser(user, getUserAndRole(user.getId()));
    }

    /***
     * 根据token获取用户权限
     *
     * @param token token
     * @return 用户权限
     */
    public SecurityUser checkAccountByToken(String token) {
        SystemUser user = null;
        List<SystemUser> loginList = systemUserMapper.selectList(new QueryWrapper<SystemUser>().eq("login_token", token));
        if (!CollectionUtils.isEmpty(loginList)) {
            user = loginList.get(0);
        }
        return user != null ? new SecurityUser(user, getUserAndRole(user.getId())) : null;
    }

    /**
     * 角色信息获取存储
     *
     * @param userId 用户id
     * @return 用户角色列表
     */
    private List<SystemRole> getUserAndRole(String userId) {
        List<SystemUserRole> userRoles = systemUserRoleMapper.selectList(new QueryWrapper<SystemUserRole>().eq("user_id", userId));
        List<SystemRole> roleList = new LinkedList<>();
        for (SystemUserRole userRole : userRoles) {
            SystemRole role = systemRoleMapper.selectById(userRole.getRoleId());
            roleList.add(role);
        }
        return roleList;
    }

}