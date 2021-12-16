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
 * @create: 2021-12-16 09:30
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CrmWork对象", description = "CrmWork对象")
public class CrmWork extends Model<CrmWork> {

    @TableId(value = "work_id")
    @ApiModelProperty(value = "工作台ID")
    private String workId;

    @ApiModelProperty(value = "工作台名称")
    private String workName;

    @ApiModelProperty(value = "工作台类型")
    private String workType;

    @ApiModelProperty(value = "工作台详细介绍")
    private String workTitle;

    @ApiModelProperty(value = "是否展示")
    private String workShow;

    @ApiModelProperty(value = "工作台计数")
    private String workCount;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "上一层级ID")
    private String pid;

    @Override
    public Serializable pkVal() {
        return this.workId;
    }
}