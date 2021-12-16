package top.ycshang.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.ycshang.crm.common.entity.SystemMenu;
import top.ycshang.crm.common.vo.DictType;
import top.ycshang.crm.common.vo.MenuVo;

import java.util.List;
import java.util.Set;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 11:10
 **/
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {
    @Select("SELECT mc.menu_code FROM `system_menu` mc ,system_role_menu srm WHERE mc.id=srm.menu_id AND srm.role_id=#{0} ")
    Set<String> getAllMenus(String rId);

    @Select("SELECT ma.menu_auth FROM system_menu ma ,system_role_menu srm WHERE ma.id=srm.menu_id AND srm.role_id=#{0} ")
    Set<String> getPermission(String rId);

    @Select("SELECT *  FROM system_menu sm  WHERE sm.parent_id=#{0} ")
    List<MenuVo> getMenuListById(String l);

    @Select("SELECT *  FROM system_menu sm  WHERE sm.parent_id='1' OR sm.parent_id='0' ")
    List<MenuVo> getMenuListByTree();

    @Select("SELECT  sm.id,sm.menu_name,sm.parent_id,sm.menu_code FROM system_menu sm,system_role_menu srm " +
            "WHERE sm.id=srm.menu_id AND sm.parent_id=#{pId} AND srm.role_id=#{rId} order by sm.id ")
    List<MenuVo> getMenuList(@Param("pId") String pId, @Param("rId") String rId);

    @Select("SELECT * FROM system_menu sm")
    List<MenuVo> getList();

    @Select("SELECT DISTINCT menu_type as value, menu_type as label  from system_menu where menu_type is not null ")
    List<DictType> getDict();
}