package top.ycshang.crm.common.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import top.ycshang.crm.common.entity.SystemRole;
import top.ycshang.crm.common.entity.SystemUser;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

/**
 * @program: my-crm
 * @description:
 * SecurityUser 获取登录用户的详情信息
 * 这个类有的敏感属性不需要被序列化，可以加上 transient 关键字
 * @author: ycshang
 * @create: 2021-12-16 10:09
 **/
@Data
@Slf4j
public class SecurityUser implements UserDetails, Serializable {
    /**
     * 当前login用户
     */
    private transient SystemUser loginUser;
    /**
     * 角色列表
     */
    private transient List<SystemRole> roleList;

    /**
     * 角色代号
     */
    private transient String roleEn;

    /**
     * 状态：0 正常， 1 禁用
     */
    private String isLock;

    public SecurityUser() {

    }

    public SecurityUser(SystemUser user) {
        if (user != null) {
            this.loginUser = user;
        }
    }

    public SecurityUser(SystemUser user, List<SystemRole> roleList) {
        if (user != null) {
            this.loginUser = user;
            this.roleList = roleList;
            if (!CollectionUtils.isEmpty(roleList)) {
                CharSequence separator;
                StringJoiner roleCodes = new StringJoiner(",", "[", "]");
                roleList.forEach(e -> roleCodes.add(e.getRoleEn()));
                this.roleEn = roleCodes.toString();
            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}