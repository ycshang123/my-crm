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
 * @create: 2021-12-16 09:30
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SystemLog对象", description = "系统日志")
public class SystemLog extends Model<SystemLog> {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "业务名称")
    private String serviceName;

    @ApiModelProperty(value = "业务地址")
    private String serviceUrl;

    @ApiModelProperty(value = "IP")
    private String requestIp;

    @ApiModelProperty(value = "用户id，0:未登录用户操作")
    private String userId;

    @ApiModelProperty(value = "运行状态")
    private Integer runStatus;

    @ApiModelProperty(value = "后台运行时间")
    private String consumingTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "app,pc")
    private String serviceType;

    @ApiModelProperty(value = "post,get")
    private String httpMethod;

    @ApiModelProperty(value = "浏览器")
    private String ua;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}