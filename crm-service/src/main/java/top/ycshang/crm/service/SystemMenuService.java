package top.ycshang.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ycshang.crm.common.entity.SystemMenu;
import top.ycshang.crm.common.vo.DictType;
import top.ycshang.crm.common.vo.MenuVo;
import top.ycshang.crm.common.vo.RoleVo;

import java.util.List;
import java.util.Map;

public interface SystemMenuService extends IService<SystemMenu> {
    /**
     * 获取树形菜单
     *
     * @return List<MenuVo>
     */
    List<MenuVo> getAllMenusByElTree();

    /**
     * @return Map<String, List < SystemMenuVo>>
     */
    Map<String, List<MenuVo>> getMenusList();

    /**
     * 根据角色id获取角色vo
     *
     * @param roleId 角色ID
     * @return SystemRoleVo
     */
    RoleVo getTree(String roleId);

    /**
     * 获取字典
     * @return
     */
    List<DictType> getDict();
}