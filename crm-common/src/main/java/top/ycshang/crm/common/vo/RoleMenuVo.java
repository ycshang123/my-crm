package top.ycshang.crm.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ycshang.crm.common.entity.SystemRoleMenu;

import java.util.List;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 10:08
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenuVo extends SystemRoleMenu {
    private List<SystemRoleMenu> sysMenus;
    private List<String> children;
}