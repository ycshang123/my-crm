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
public class SystemRole extends Model<SystemRole> {

    @TableId(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String roleEn;

    private String roleCn;

    private Date createTime;

    private Date updateTime;

    private String describeText;

    private String isEnable;

    private String readonly;

    private String createUser;

    private String updateUser;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}