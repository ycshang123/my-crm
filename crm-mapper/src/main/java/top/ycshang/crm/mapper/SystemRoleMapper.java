package top.ycshang.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.ycshang.crm.common.entity.SystemRole;
import top.ycshang.crm.common.vo.RoleVo;

import java.util.List;

public interface SystemRoleMapper extends BaseMapper<SystemRole> {
    /**
     * 查询所有系统角色
     *
     * @return List<RoleVo>
     */
    @Select("SELECT *,id roleId FROM system_role")
    List<RoleVo> getList();

    /**
     * 分页查询系统角色
     *
     * @param pageNum 页码
     * @param pageRow 每页数量
     * @return List<RoleVo>
     */
    @Select("SELECT *,id roleId FROM system_role limit #{0},#{1} ")
    List<RoleVo> getRolesByPage(@Param("pageNum") int pageNum, @Param("pageRow") int pageRow);

}