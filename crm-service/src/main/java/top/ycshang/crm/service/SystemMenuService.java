package top.ycshang.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ycshang.crm.common.entity.SystemMenu;
import top.ycshang.crm.common.vo.*;

import java.util.List;
import java.util.Map;

public interface SystemMenuService extends IService<SystemMenu> {
    /**
     * 获取树形菜单
     *
     * @return List
     */
    List<SystemMenuVo> getAllMenusByElTree();

    /**
     * 获取菜单
     *
     * @return Map
     */
    Map<String, List<SystemMenuVo>> getMenusList();

    /**
     * 根据角色id获取角色vo
     *
     * @param roleId 角色ID
     * @return SystemRoleVo
     */
    SystemRoleVo getTree(String roleId);

    /**
     * 获取字典
     *
     * @return List
     */
    List<DictType> getDict();
}