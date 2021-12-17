package top.ycshang.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.ycshang.crm.common.entity.SystemMenu;
import top.ycshang.crm.common.vo.DictType;
import top.ycshang.crm.common.vo.SystemMenuVo;

import java.util.List;
import java.util.Set;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 11:10
 **/
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {
    @Select("SELECT sm.menu_code FROM system_menu sm ,system_role_menu srm WHERE sm.id=srm.menu_id AND srm.role_id=#{0} ")
    Set<String> getAllMenus(String rId);

    @Select("SELECT mm.menu_auth FROM system_menu mm ,system_role_menu srm WHERE mm.id=srm.menu_id AND srm.role_id =#{0} ")
    Set<String> getPermission(String rId);

    @Select("SELECT * FROM system_menu sm WHERE sm.parent_id=#{0} ")
    List<SystemMenuVo> getMenuListById(String l);

    @Select("SELECT * FROM system_menu sm WHERE sm.parent_id='1' OR sm.parent_id='0' ")
    List<SystemMenuVo> getMenuListByTree();

    @Select("SELECT sm.id,sm.menu_name,sm.parent_id,sm.menu_code FROM system_menu sm,system_role_menu srm " +
            "WHERE sm.id=srm.menu_id AND sm.parent_id=#{pId} AND srm.role_id=#{rId} ORDER BY sm.id ")
    List<SystemMenuVo> getMenuList(@Param("pId") String pId, @Param("rId") String rId);

    @Select("SELECT * FROM system_menu sm ")
    List<SystemMenuVo> getList();

    @Select("SELECT DISTINCT menu_type AS value, menu_type AS label from system_menu WHERE menu_type IS NOT NULL ")
    List<DictType> getDict();
}