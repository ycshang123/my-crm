package top.ycshang.crm.service.logger;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.ycshang.crm.common.entity.SystemLog;
import top.ycshang.crm.common.entity.SystemUser;
import top.ycshang.crm.common.util.IpUtil;
import top.ycshang.crm.common.vo.SystemResult;
import top.ycshang.crm.mapper.SystemUserMapper;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @program: my-crm
 * @description:
 * @author: ycshang
 * @create: 2021-12-17 20:22
 **/
@Aspect
@Configuration
@Slf4j
public class SystemLogAspect {

    @Resource
    private SystemUserMapper systemUserMapper;

    @Pointcut("execution(* top.mqxu.crm.controller.*Controller.*(..)))")
    public void systemLogAspect() {
    }

    @Around(value = "systemLogAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        // token
        String url = request.getRequestURL().toString();
        String ip = IpUtil.getIpAddress(request);
        String token = request.getHeader("Authorization");

        // AOP反射机制获取织入点处
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 提醒：进行反射和方法的获取
        Method method = signature.getMethod();
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        String methodName = "";
        if (apiOperation != null) {
            methodName = apiOperation.value();
        }
        long startTime = System.currentTimeMillis();
        SystemResult<?> result = (SystemResult<?>) joinPoint.proceed(joinPoint.getArgs());
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        SystemLog systemLog = new SystemLog();
        systemLog.setServiceName(methodName);
        systemLog.setServiceUrl(url);
        systemLog.setRequestIp(ip);
        systemLog.setCreateTime(new Date());
        systemLog.setUpdateTime(new Date());
        if (token == null) {
            systemLog.setUserId("0");
        } else {
            SystemUser selectOne = systemUserMapper.selectOne(new QueryWrapper<SystemUser>().eq("login_token", token));
            if (selectOne != null) {
                systemLog.setUserId(selectOne.getId());
            }
        }
        systemLog.setRunStatus(result.getCode());
        systemLog.setConsumingTime(totalTime + " ms");
        systemLog.insert();
        return result;
    }

}