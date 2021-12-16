package top.ycshang.crm.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ycshang.crm.common.entity.SystemMenu;

import java.util.List;

/**
 * @program: my-crm
 * @description: 系统菜单VO类
 * @author: ycshang
 * @create: 2021-12-16 10:03
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuVo extends SystemMenu {
    private List<MenuVo> children;
    private List<MenuVo> menuList;
}