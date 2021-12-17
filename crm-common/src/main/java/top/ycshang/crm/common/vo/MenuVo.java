package top.ycshang.crm.common.vo;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ycshang.crm.common.entity.SystemMenu;

import java.io.Serializable;
import java.util.List;

/**
 * @program: my-crm
 * @description: 系统菜单VO类
 * @author: ycshang
 * @create: 2021-12-16 10:03
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuVo extends SystemMenu implements Serializable {
    List<MenuVo> children = Lists.newArrayList();
}