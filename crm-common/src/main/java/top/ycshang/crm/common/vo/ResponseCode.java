package top.ycshang.crm.common.vo;


/**
 * @program: my-crm
 * @description: 系统菜单VO类
 * @author: ycshang
 * @create: 2021-12-16 10:03
 **/
public enum ResponseCode {
    /**
     * 定义枚举值
     */
    ERROR(0, "ERROR"),
    SUCCESS(1, "SUCCESS"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT"),
    NEED_LOGIN(10, "NEED_LOGIN");


    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
