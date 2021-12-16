package top.ycshang.crm.common.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 09:33
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SystemRoleMenu对象", description = "系统角色菜单")
public class SystemRoleMenu extends Model<SystemRoleMenu> {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建者")
    private String createUser;

    @ApiModelProperty(value = "更新者")
    private String updateUser;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}