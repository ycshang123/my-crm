package top.ycshang.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ycshang.crm.common.entity.SystemUser;
import top.ycshang.crm.common.vo.SystemMenuVo;
import top.ycshang.crm.common.vo.SystemUserInfoVo;
import top.ycshang.crm.common.vo.SystemUserVo;

import java.util.List;
import java.util.Map;

public interface SystemUserService extends IService<SystemUser> {
    /**
     * 保存用户和角色
     *
     * @param systemUserVo 系统用户vo
     * @return int
     */
    int saveUserAndRole(SystemUserVo systemUserVo);

    /**
     * 更新用户和角色
     *
     * @param systemUserVo 系统用户vo
     * @return int
     */
    int updateUserAndRole(SystemUserVo systemUserVo);

    /**
     * 根据token获取用户信息
     *
     * @param token token
     * @return 用户信息
     */
    SystemUserInfoVo getInfo(String token);

    /**
     * 根据token获取用户菜单权限
     *
     * @param token token
     * @return 菜单权限
     */
    Map<String, List<SystemMenuVo>> getPermissions(String token);
}