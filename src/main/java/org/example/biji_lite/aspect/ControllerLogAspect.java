package org.example.biji_lite.aspect;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Controller 层 AOP 日志切面
 * 记录所有 Controller 请求的入参、出参、耗时等信息
 */
@Slf4j
@Aspect
@Component
public class ControllerLogAspect {

    /**
     * 修复：配置支持 Java 8 时间类型的 ObjectMapper
     */
    private final ObjectMapper objectMapper;

    public ControllerLogAspect() {
        this.objectMapper = new ObjectMapper();
        // 注册 Java 8 时间模块，支持 LocalDateTime 等类型
        this.objectMapper.registerModule(new JavaTimeModule());
        // 禁止将日期序列化为时间戳（保持 ISO 格式）
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * 切点：匹配所有 Controller 下的所有方法
     */
    @Pointcut("execution(* org.example.biji_lite.controller..*(..))")
    public void controllerPointcut() {
    }

    /**
     * 环绕通知：记录请求日志
     */
    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求相关信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();

        // 构建请求日志
        StringBuilder requestLog = new StringBuilder();
        requestLog.append("\n========== Controller 请求开始 ==========");

        if (request != null) {
            requestLog.append("\n请求路径: ").append(request.getMethod()).append(" ").append(request.getRequestURI());
        }

        requestLog.append("\n类名方法: ").append(className).append(".").append(methodName);

        // 记录请求参数
        String params = Arrays.stream(joinPoint.getArgs())
                .map(arg -> {
                    try {
                        return arg != null ? objectMapper.writeValueAsString(arg) : "null";
                    } catch (Exception e) {
                        return arg != null ? arg.toString() : "null";
                    }
                })
                .collect(Collectors.joining(", "));
        requestLog.append("\n请求参数: [").append(params).append("]");

        // 记录开始时间
        long startTime = System.currentTimeMillis();

        // 执行目标方法
        Object result;
        try {
            result = joinPoint.proceed();

            // 记录成功日志
            long elapsedTime = System.currentTimeMillis() - startTime;
            requestLog.append("\n执行耗时: ").append(elapsedTime).append("ms");

            // 记录响应结果（只记录部分，避免过大）
            String resultStr = result != null ? objectMapper.writeValueAsString(result) : "null";
            if (resultStr.length() > 500) {
                resultStr = resultStr.substring(0, 499) + "...";
            }
            requestLog.append("\n响应结果: ").append(resultStr);

            log.info(requestLog.toString());

            return result;

        } catch (Exception e) {
            // 记录异常日志
            long elapsedTime = System.currentTimeMillis() - startTime;
            requestLog.append("\n执行耗时: ").append(elapsedTime).append("ms");
            requestLog.append("\n异常信息: ").append(e.getClass().getSimpleName()).append(": ").append(e.getMessage());

            log.error(requestLog.toString(), e);

            // 继续抛出异常，交给全局异常处理器
            throw e;
        } finally {
            log.info("========== Controller 请求结束 ==========\n");
        }
    }
}