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
 * @create: 2021-12-16 09:30
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SystemMenu对象", description = "系统菜单")
public class SystemMenu extends Model<SystemMenu> {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    @ApiModelProperty(value = "pid")
    private String parentId;

    private String menuCode;

    @ApiModelProperty(value = "url")
    private String menuPath;

    @ApiModelProperty(value = "权限定义")
    private String menuAuth;

    @ApiModelProperty(value = "vue动态组件")
    private String menuComponent;

    @ApiModelProperty(value = "权限名称")
    private String menuName;

    @ApiModelProperty(value = "排序")
    private Integer menuSort;

    @ApiModelProperty(value = "资源图标")
    private String menuIcon;

    @ApiModelProperty(value = "种类 menu、button")
    private String menuType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否启用")
    private String isEnable;

    @ApiModelProperty(value = "创建者	")
    private String createUser;

    @ApiModelProperty(value = "describe")
    private String describeText;

    @ApiModelProperty(value = "更新者")
    private String updateUser;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}