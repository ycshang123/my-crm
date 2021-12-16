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
 * @create: 2021-12-16 09:32
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SystemRadar对象", description = "系统雷达")
public class SystemRadar extends Model<SystemRadar> {

    private Long id;

    private String item;

    private String persion;

    private String team;

    private String dep;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}