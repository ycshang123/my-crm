package top.ycshang.crm.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ycshang.crm.common.entity.SystemRole;
import top.ycshang.crm.common.entity.SystemUser;

import java.io.Serializable;
import java.util.Set;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:25
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemUserInfoVo extends SystemUser implements Serializable {
    private Set<String> menuList;

    private Set<String> permissionList;

    @TableField(exist = false)
    private SystemRole role;

    private Set<String> roles = Sets.newHashSet();

    private Set<MenuVo> menus = Sets.newHashSet();

}