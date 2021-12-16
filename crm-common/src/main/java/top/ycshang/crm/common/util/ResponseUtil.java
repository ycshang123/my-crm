package top.ycshang.crm.common.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import top.ycshang.crm.common.vo.SystemResult;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-16 10:12
 **/
@Slf4j
public class ResponseUtil {

    public static void out(ServletResponse response, SystemResult<?> systemResult) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(systemResult));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}