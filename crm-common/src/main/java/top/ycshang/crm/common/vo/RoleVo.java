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
 * @create: 2021-12-16 10:08
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleVo extends SystemRole {
    private List<SystemUser> systemUsers;
    private List<RoleMenuVo> roleMenuVos;
    private List<MenuVo> menuVos;
    private List<String> checkedIds;
}