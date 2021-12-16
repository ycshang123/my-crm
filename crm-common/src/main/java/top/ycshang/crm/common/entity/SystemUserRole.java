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
 * @create: 2021-12-16 09:34
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SystemUserRole对象", description = "系统用户")
public class SystemUserRole extends Model<SystemUserRole> {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建者")
    private String createUser;

    private String updateUser;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}