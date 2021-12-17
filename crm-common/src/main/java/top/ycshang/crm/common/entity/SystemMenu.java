package top.ycshang.crm.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 09:30
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemMenu extends Model<SystemMenu> {

    @TableId(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String parentId;

    private String menuCode;

    private String menuPath;

    private String menuAuth;

    private String menuComponent;

    private String menuName;

    private Integer menuSort;

    private String menuIcon;

    private String menuType;

    private Date createTime;

    private Date updateTime;

    private String isEnable;

    private String createUser;

    private String describeText;

    private String updateUser;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}