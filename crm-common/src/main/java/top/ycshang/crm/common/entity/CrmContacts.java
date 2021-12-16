package top.ycshang.crm.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
 * @create: 2021-12-16 09:29
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CrmContacts对象", description = "CRM联系人")
public class CrmContacts extends Model<CrmContacts> {

    @TableId(value = "contacts_id")
    @ApiModelProperty(value = "联系人ID")
    private String contactsId;

    @ApiModelProperty(value = "联系人姓名")
    private String contactsName;

    @ApiModelProperty(value = "最近沟通时间")
    private Date lastTime;

    @ApiModelProperty(value = "手机")
    private String contactsMobile;

    @ApiModelProperty(value = "电话")
    private String contactsTelephone;

    @ApiModelProperty(value = "邮箱")
    private String contactsEmail;

    @ApiModelProperty(value = "公司职务")
    private String title;

    @ApiModelProperty(value = "客户ID")
    private Integer clientId;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人ID")
    private Long createUserId;

    @ApiModelProperty(value = "负责人ID")
    private Long ownerUserId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @Override
    public Serializable pkVal() {
        return this.contactsId;
    }
}