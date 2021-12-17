package top.ycshang.crm.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ycshang.crm.common.entity.SystemRole;
import top.ycshang.crm.common.entity.SystemUser;

import java.util.List;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:25
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemRoleVo extends SystemRole {
    private List<SystemUser> systemUsers;
    private List<SystemRoleMenusVo> sysRoleMenus;
    private List<SystemMenuVo> sysMenuVo;
    private List<String> checkedIds;
}