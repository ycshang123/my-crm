package top.ycshang.crm.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: my-crm
 * @description: 全局解决跨域
 * @author: ycshang
 * @create: 2021-12-17 16:21
 **/
@Configuration
public class CorsConfig {
    private CorsConfiguration config() {
        List<String> list = new ArrayList<>();
        list.add("*");
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowCredentials(true);
        cc.setAllowedOriginPatterns(list);
        cc.addAllowedHeader("*");
        cc.addAllowedMethod("*");
        return cc;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config());
        return new CorsFilter(source);
    }
}