package top.ycshang.crm.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 10:11
 **/
@Data
@ApiModel(description = "系统管理 - 用户角色关联表 查询参数")
public class UserRoleVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "用户ids")
    private String userIds;
}