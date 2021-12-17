package top.ycshang.crm.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import top.ycshang.crm.security.auth.*;


import javax.annotation.Resource;

/**
 * @program: my-crm
 * @description:Security 核心配置类
 * @author: ycshang
 * @create: 2021-12-17 20:15
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private IgnoreUrlConfig ignoreUrlConfig;

    /**
     * 认证token
     */
    @Resource
    private SystemDiyAuthenticationFilter systemDiyAuthenticationFilter;

    /**
     * 访问权限
     */
    @Resource
    private SystemAuthenticationEntryPoint systemAuthenticationEntryPoint;

    /**
     * 校验过滤器
     */
    @Resource
    private SystemAuthenticationProcessingFilter systemAuthenticationProcessingFilter;

    /**
     * url所需角色
     */
    @Resource
    private SystemUrlFilterInvocationSecurityMetadataSource systemUrlFilterInvocationSecurityMetadataSource;
    /**
     * 认证权限处，按照角色处理
     */
    @Resource
    private SystemUrlAccessDecisionManager systemUrlAccessDecisionManager;
    /**
     * 拦截403响应内容
     */
    @Resource
    private SystemUrlAccessDeniedHandler systemUrlAccessDeniedHandler;


    /**
     * 权限配置
     *
     * @param http http
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/druid/*");
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.antMatcher("/**").authorizeRequests();
        // 禁用CSRF 开启跨域
        http.csrf().disable().cors();
        // 未登录认证异常
        http.exceptionHandling().authenticationEntryPoint(systemAuthenticationEntryPoint);
        http.exceptionHandling().accessDeniedHandler(systemUrlAccessDeniedHandler);
        // url权限认证处理
        registry.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(systemUrlFilterInvocationSecurityMetadataSource);
                o.setAccessDecisionManager(systemUrlAccessDecisionManager);
                return o;
            }
        });

        // 设置为无状态
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        for (String url : ignoreUrlConfig.getNocheck().getIgnoreUrls()) {
            registry.antMatchers(url).permitAll();
        }

        //registry.antMatchers("/api/role/getAllMenus", "/druid").permitAll();
        //http.authorizeRequests().antMatchers("/api/role/getAllMenus").permitAll();
        //registry.antMatchers("/", "/api/role/**", "/druid/**").permitAll();
        registry.antMatchers(HttpMethod.OPTIONS, "/**").denyAll();
        // 自定义过滤器在登录时认证用户名、密码
        http.addFilterAt(systemAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(systemDiyAuthenticationFilter, BasicAuthenticationFilter.class);
    }

    /**
     * 例外的url
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET,
                "/favicon.ico",
                "/**/*.png",
                "/**/*.ttf",
                "/*.html",
                "/**/css/bootstrap.min.css",
                "/**/js/jquery.min.js",
                "/*/css/bootstrap.min.css",
                "/*/js/jquery.min.js",
                "/**/*.js",
                "/**/*.css",
                "/druid/**",
                "/**/*.js");
        web.ignoring().antMatchers("/druid/**");


    }
}