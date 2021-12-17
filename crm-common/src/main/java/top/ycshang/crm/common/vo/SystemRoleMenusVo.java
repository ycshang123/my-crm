package top.ycshang.crm.common.vo;

import top.ycshang.crm.common.entity.SystemRoleMenu;

import java.util.List;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:24
 **/
public class SystemRoleMenusVo extends SystemRoleMenu {
    private List<SystemMenuVo> sysMenus;
    private List<String> children;
}