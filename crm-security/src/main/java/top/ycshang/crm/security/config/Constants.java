package top.ycshang.crm.security.config;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 15:57
 **/
public class Constants {

    public static final String SECURITY_SALT = "HelloWorld";
    /**
     * 请求头
     */
    public static final String REQUEST_HEADER = "auth-token";
    public static final String REQUEST_HEADER_CONTENT_TYPE = "application/json";
    public static final String REQUEST_METHOD_POST = "POST";
    public static final String REQUEST_METHOD_GET = "GET";
    private static final int HASH_ITERATIONS = 1;
    public static String PROJECT_ROOT_DIRECTORY = System.getProperty("user.dir");
}