package top.ycshang.crm.common.vo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 10:10
 **/
public class SystemResult<T> implements Serializable {
    private int code;

    private String msg;

    private T data;

    private SystemResult(int code) {
        this.code = code;
    }

    private SystemResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private SystemResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private SystemResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    @JsonIgnore
    //使之不在json序列化结果当中
    //4.判断这个响应是不是一个正确的响应
    public boolean isSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    //5.定义返回对象的方法
    public static <T> SystemResult<T> createBySuccess() {
        return new SystemResult<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> SystemResult<T> createBySuccessMessage(String msg) {
        return new SystemResult<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> SystemResult<T> createBySuccess(T data) {
        return new SystemResult<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> SystemResult<T> createByCodeSuccess(int code, String msg, T data) {
        return new SystemResult<T>(code, msg, data);
    }

    public static <T> SystemResult<T> createBySuccess(String msg, T data) {
        return new SystemResult<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> SystemResult<T> createByError() {
        return new SystemResult<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> SystemResult<T> createByErrorMessage(String errorMessage) {
        return new SystemResult<T>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> SystemResult<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
        return new SystemResult<T>(errorCode, errorMessage);
    }

    /**
     * 将request参数值转为json
     */
    public static JSONObject request2Json(HttpServletRequest request) {
        JSONObject requestJson = new JSONObject();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] pv = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pv.length; i++) {
                if (pv[i].length() > 0) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(pv[i]);
                }
            }
            requestJson.put(paramName, sb.toString());
        }
        return requestJson;
    }

    /**
     * 将request转JSON
     * 并且验证非空字段
     */
    public static JSONObject convert2JsonAndCheckRequiredColumns(HttpServletRequest request, String requiredColumns) {
        JSONObject jsonObject = request2Json(request);
        hasAllRequired(jsonObject, requiredColumns);
        return jsonObject;
    }

    /**
     * 验证是否含必填字段
     *
     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
     */
    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
        if (!isNullOrEmpty(requiredColumns)) {
            //验证字段非空
            String[] columns = requiredColumns.split(",");
            StringBuilder missCol = new StringBuilder();
            for (String column : columns) {
                Object val = jsonObject.get(column.trim());
                if (isNullOrEmpty(val)) {
                    missCol.append(column).append("  ");
                }
            }
            if (!isNullOrEmpty(missCol.toString())) {
                jsonObject.clear();
                jsonObject.put("code", "400");
                jsonObject.put("msg", "缺少必填参数:" + missCol.toString().trim());
                jsonObject.put("info", new JSONObject());

            }
        }
    }

    public static boolean isNullOrEmpty(String str) {
        return null == str || "".equals(str) || "null".equals(str);
    }

    public static boolean isNullOrEmpty(Object obj) {
        return null == obj || "".equals(obj);
    }

}