package top.ycshang.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ycshang.crm.common.entity.SystemUser;
import top.ycshang.crm.common.vo.MenuVo;
import top.ycshang.crm.common.vo.UserInfoVo;
import top.ycshang.crm.common.vo.UserVo;

import java.util.List;
import java.util.Map;

public interface SystemUserService extends IService<SystemUser> {
    /**
     * 保存用户和角色
     *
     * @param userVo 用户vo
     * @return int
     */
    int saveUserAndRole(UserVo userVo);

    /**
     * 更新用户和角色
     *
     * @param userVo 用户vo
     * @return int
     */
    int updateUserAndRole(UserVo userVo);

    /**
     * 根据token获取用户信息
     *
     * @param token token
     * @return 用户信息
     */
    UserInfoVo getInfo(String token);

    /**
     * 根据token获取用户菜单权限
     *
     * @param token token
     * @return 菜单权限
     */
    Map<String, List<MenuVo>> getPermissions(String token);
}