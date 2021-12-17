package top.ycshang.crm.security.api;

public interface IErrorCode {
    /**
     * 错误编码
     *
     * @return
     */
    long getCode();

    /**
     * 错误描述
     *
     * @return
     */
    String getMsg();
}
