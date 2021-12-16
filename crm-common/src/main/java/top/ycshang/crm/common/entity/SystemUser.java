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
 * @create: 2021-12-16 09:33
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SystemUser对象", description = "系统用户")
public class SystemUser extends Model<SystemUser> {

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    @ApiModelProperty(value = "账号")
    private String accountName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "0:男 1:女")
    private String sex;

    @ApiModelProperty(value = "手机")
    private String tel;

    @ApiModelProperty(value = "Email")
    private String email;

    @ApiModelProperty(value = "头像")
    private String photo;

    @ApiModelProperty(value = "是否锁定")
    private String isLock;

    @ApiModelProperty(value = "盐值")
    private String pwdSalt;

    @ApiModelProperty(value = "token")
    private String loginToken;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建者")
    private String createUser;

    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "更新者")
    private String updateUser;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}