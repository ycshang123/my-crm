package top.ycshang.crm.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 09:30
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class CrmWork extends Model<CrmWork> {

    @TableId(value = "work_id")
    private String workId;

    private String workName;

    private String workType;

    private String workTitle;

    private String workShow;

    private String workCount;

    private Date updateTime;

    private String remark;

    private String pid;

}