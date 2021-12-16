package top.ycshang.crm.common.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 09:31
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SystemOauth对象", description = "系统授权信息")
public class SystemOauth extends Model<SystemOauth> {

    private String id;

    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}