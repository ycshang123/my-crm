package top.ycshang.crm.security.auth;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import top.ycshang.crm.common.entity.SystemUser;
import top.ycshang.crm.common.vo.SecurityUser;
import top.ycshang.crm.mapper.SystemUserMapper;
import top.ycshang.crm.security.config.Constants;
import top.ycshang.crm.service.impl.UserDetailsServiceImpl;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @program: my-crm
 * @description: 自定义认证处理
 * @author: ycshang
 * @create: 2021-12-17 15:20
 **/
@Component
public class SystemAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String accountName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        System.out.println(accountName);
        System.out.println(password);
        // 查看用户是否存在
        SecurityUser userInfo = (SecurityUser) userDetailsService.loadUserByUsername(accountName);
        System.out.println(userInfo);
        if (userInfo == null) {
            throw new UsernameNotFoundException("登录的账户不存在，请确认");
        }
        // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!new BCryptPasswordEncoder().matches(password, userInfo.getLoginUser().getPassword())) {
            throw new BadCredentialsException("密码不正确,请检查");
        }

        if ("1".equals(userInfo.getLoginUser().getIsLock())) {
            throw new LockedException("该账号已被Lock");
        }
        // 角色
        String roleCodes = userInfo.getRoleEn();
        String token = Jwts.builder().claim("role_login", roleCodes).setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 25 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, Constants.SECURITY_SALT)
                .compact();

        SystemUser systemUser = systemUserMapper.selectById(userInfo.getLoginUser().getId());
        systemUser.setLoginToken(token);
        systemUserMapper.updateById(systemUser);
        userInfo.getLoginUser().setLoginToken(token);
        return new UsernamePasswordAuthenticationToken(userInfo, password, userInfo.getAuthorities());
    }

}