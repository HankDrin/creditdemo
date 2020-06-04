package com.service.common.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.common.annotation.PrintLog;
import com.service.common.enums.log.LogLevelEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统方法日志打印
 *
 */
@Aspect
@Component
@Slf4j
public class PrintLogAspect {

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    /**
     * 方法参数名获取组件
     */
    @Autowired
    private ParameterNameDiscoverer parameterNameDiscoverer;

    /**
     * 拦截系统带@PrintLog，打印日志
     *
     * @param proceedingJoinPoint 切入点对象
     * @return 方法执行结果对象
     * @throws Throwable 目标方法可能抛出的异常
     */
    @Around("within(com.service..*) && @annotation(com.service.common.annotation.PrintLog)")
    public Object printAutoLogs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Signature signature = proceedingJoinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        }

        MethodSignature methodSignature = (MethodSignature) signature;
        Object[] args = proceedingJoinPoint.getArgs().clone();
        Method method = methodSignature.getMethod();
        String targetClassName = method.getDeclaringClass().getName();
        String targetMethodName = methodSignature.getName();
        PrintLog printLog = methodSignature.getMethod().getAnnotation(PrintLog.class);

        try {
            String inMessage =
                    "begin invoke [" + targetClassName + "].[" + targetMethodName + "], args: " + generateInMethodLog(
                    methodSignature, args);
            printLog(proceedingJoinPoint.getTarget(), inMessage, printLog.level());
        } catch (Exception e) {
            log.warn("方法日志入参打印失败", e);
        }

        Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        try {
            String returnResult = StringUtils.EMPTY;
            if (printLog.isPrintReturnResult()) {
                returnResult = "result: " + generateOutMethodLog(result);
            }
            String outMessage = "end invoke [" + targetClassName + "].[" + targetMethodName + "]. " + returnResult;
            printLog(proceedingJoinPoint.getTarget(), outMessage, printLog.level());
        } catch (Exception e) {
            log.warn("方法日志返回结果打印失败", e);
        }
        return result;
    }

    /**
     * 打印方法入参日志
     *
     * @param methodSignature 目标方法签名
     * @param args            参数
     */
    private String generateInMethodLog(MethodSignature methodSignature, Object[] args) throws JsonProcessingException {
        if (args.length == 0) {
            return "{}";
        }

        String[] parameterNames = parameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
        Map<String, Object> paramData = new HashMap<>(5);
        for (int i = 0; i < parameterNames.length; i++) {
            if(args[i] instanceof ApplicationEvent){
                paramData.put(parameterNames[i], jacksonObjectMapper.writeValueAsString(args[i]));
            } else {
                paramData.put(parameterNames[i], args[i]);
            }
        }
        return jacksonObjectMapper.writeValueAsString(paramData);
    }

    /**
     * 打印方法结束日志
     *
     * @param result        方法返回对象
     */
    private String generateOutMethodLog(Object result) throws JsonProcessingException {
        if (result == null) {
            return "{}";
        }
        return jacksonObjectMapper.writeValueAsString(result);
    }

    /**
     * 根据日志级别打印日志
     *
     * @param message      消息
     * @param logLevelEnum 日志级别
     */
    private void printLog(Object target, String message, LogLevelEnum logLevelEnum) {
        Logger logger = LoggerFactory.getLogger(target.getClass());
        switch (logLevelEnum) {
            case INFO:
                logger.info(message);
                return;
            case DEBUG:
                logger.debug(message);
                return;
            default:
                logger.info(message);
        }
    }

}
