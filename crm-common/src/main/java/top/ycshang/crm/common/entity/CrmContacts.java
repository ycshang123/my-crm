package top.ycshang.crm.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@EqualsAndHashCode(callSuper = true)
@Data
public class CrmContacts extends Model<CrmContacts> {

    @TableId(value = "contacts_id")
    private String contactsId;

    private String contactsName;

    private Date lastTime;

    private String contactsMobile;

    private String contactsTelephone;

    private String contactsEmail;

    private String title;

    private Integer clientId;

    private String address;

    private String remark;

    private Long createUserId;

    private Long ownerUserId;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private String clientName;

    @Override
    public Serializable pkVal() {
        return this.contactsId;
    }
}