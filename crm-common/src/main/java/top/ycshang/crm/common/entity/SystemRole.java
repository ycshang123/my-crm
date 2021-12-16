package top.ycshang.crm.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
 * @create: 2021-12-16 09:32
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SystemRole对象", description = "系统角色 ")
public class SystemRole extends Model<SystemRole> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    @ApiModelProperty(value = "角色英文")
    private String roleEn;

    @ApiModelProperty(value = "角色中文")
    private String roleCn;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "描述")
    private String describeText;

    @ApiModelProperty(value = "是否启用")
    private String isEnable;

    @ApiModelProperty(value = "系统用户")
    private String readonly;

    @ApiModelProperty(value = "创建者")
    private String createUser;

    @ApiModelProperty(value = "更新者")
    private String updateUser;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}