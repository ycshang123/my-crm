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
public class SystemRoleMenu extends Model<SystemRoleMenu> {

    private Long id;

    private Long roleId;

    private Long menuId;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}