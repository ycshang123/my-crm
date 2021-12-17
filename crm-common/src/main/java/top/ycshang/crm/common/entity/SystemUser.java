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
public class SystemUser extends Model<SystemUser> {

    @TableId(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String accountName;

    private String password;

    private String realName;

    private String sex;

    private String tel;

    private String email;

    private String photo;

    private String isLock;

    private String pwdSalt;

    private String loginToken;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private Date lastLoginTime;

    private String updateUser;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}