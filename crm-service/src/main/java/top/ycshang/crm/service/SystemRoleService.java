package top.ycshang.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import top.ycshang.crm.common.entity.SystemRole;
import top.ycshang.crm.common.vo.MenuVo;

import java.util.List;
import java.util.Map;

public interface SystemRoleService extends IService<SystemRole> {
    /**
     * 查询用户和角色的接口
     *
     * @return map
     */
    Map<String, List<MenuVo>> getRoleAndUserList();

    /**
     * 设置菜单权限，尤其是获取id
     *
     * @param jsonObject jsonObject
     * @return int
     */
    int setMenus(JSONObject jsonObject);

    /**
     * 分页获取用户角色权限
     *
     * @param pageNum 当前页
     * @param pageRow 每页行数
     * @return map
     */
    Map<String, Object> getRoleAndUserByPage(int pageNum, int pageRow);
}