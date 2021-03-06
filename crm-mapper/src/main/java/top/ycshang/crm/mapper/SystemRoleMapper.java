package top.ycshang.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.ycshang.crm.common.entity.SystemRole;
import top.ycshang.crm.common.vo.SystemRoleVo;

import java.util.List;

public interface SystemRoleMapper extends BaseMapper<SystemRole> {
    /**
     * 查询所有系统角色
     *
     * @return List
     */
    @Select("SELECT *,id roleId FROM system_role")
    List<SystemRoleVo> getList();

    /**
     * 分页查询系统角色
     *
     * @param pageNum 页码
     * @param pageRow 每页数量
     * @return List
     */
    @Select("SELECT *,id roleId FROM system_role limit #{0},#{1} ")
    List<SystemRoleVo> getRolesByPage(@Param("pageNum") int pageNum, @Param("pageRow") int pageRow);

}