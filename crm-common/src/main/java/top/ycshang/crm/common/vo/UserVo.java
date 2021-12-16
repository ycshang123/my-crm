package top.ycshang.crm.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ycshang.crm.common.entity.SystemRole;
import top.ycshang.crm.common.entity.SystemUser;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 10:11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVo extends SystemUser {
    private SystemRole systemRole;
    private String roleId;
    private String roleName;
}