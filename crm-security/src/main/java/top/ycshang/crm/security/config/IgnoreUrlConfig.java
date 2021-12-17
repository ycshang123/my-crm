package top.ycshang.crm.security.config;

import io.swagger.models.Swagger;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 18:15
 **/
@Data
@Component
@ConfigurationProperties(prefix = "crm")
public class IgnoreUrlConfig {
    private final Swagger swagger = new Swagger();
    private final Nocheck nocheck = new Nocheck();

    @Data
    public static class Swagger{
        private String title;
        private String description;
        private String version;
        private String termsOfServiceUrl;
        private String contactName;
        private String contactUrl;
        private String contactEmail;
        private String license;
        private String licenseUrl;

    }

    @Data
    public static class Nocheck{
        private Integer tokenExpireTime;
        private Integer saveLoginTime;
        private Integer loginTimeLimit;
        private Integer loginAfterTime;
        private List<String> ignoreUrls;

    }
}