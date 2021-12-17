package top.ycshang.crm.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ycshang.crm.common.entity.SystemRole;
import top.ycshang.crm.common.entity.SystemUser;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:26
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemUserVo extends SystemUser {
    private SystemRole sysRole;
    private String roleId;
    private String roleName;
}