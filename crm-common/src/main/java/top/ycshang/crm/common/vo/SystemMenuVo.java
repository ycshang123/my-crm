package top.ycshang.crm.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.ycshang.crm.common.entity.SystemMenu;

import java.util.List;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 11:23
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemMenuVo extends SystemMenu {
    private List<SystemMenuVo> children;
    private List<SystemMenuVo> menuList;
}