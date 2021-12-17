package top.ycshang.crm.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.ycshang.crm.common.vo.SystemResult;
import top.ycshang.crm.security.api.ApiErrorCode;
import top.ycshang.crm.security.api.ApiException;
import top.ycshang.crm.security.api.IErrorCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: my-crm
 * @description: 自定义全局统一异常处理
 * @author: ycshang
 * @create: 2021-12-17 16:30
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义 REST 业务异常
     *
     * @param e 异常类型
     * @return SystemResult
     */
    @ExceptionHandler(value = Exception.class)
    public SystemResult<Object> handleBadRequest(Exception e) {

        if (e instanceof ApiException) {
            IErrorCode errorCode = ((ApiException) e).getErrorCode();
            if (null != errorCode) {
                log.debug("Rest request error," + errorCode);
                return SystemResult.createByErrorMessage(errorCode.toString());
            }
            log.debug("Rest request error, " + e.getMessage());
            return SystemResult.createByErrorMessage(e.getMessage());
        }
        /*
         * 参数校验异常
         */
        if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            if (bindingResult.hasErrors()) {
                List<Map<String, Object>> jsonList = new ArrayList<>();
                bindingResult.getFieldErrors().forEach(fieldError -> {
                    Map<String, Object> jsonObject = new HashMap<>(2);
                    jsonObject.put("name", fieldError.getField());
                    jsonObject.put("msg", fieldError.getDefaultMessage());
                    jsonList.add(jsonObject);
                });
                return SystemResult.createBySuccess(ApiErrorCode.FAILED.toString(), jsonList);
            }
        }

        //系统内部异常，打印异常栈
        log.error("Error: handleBadRequest StackTrace :" + e);
        return SystemResult.createByErrorMessage(ApiErrorCode.FAILED.toString());
    }
}