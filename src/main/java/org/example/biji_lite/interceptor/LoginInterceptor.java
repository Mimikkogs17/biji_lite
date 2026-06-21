package org.example.biji_lite.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.biji_lite.common.Result;
import org.example.biji_lite.common.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 登录拦截器
 * 1. 检查用户是否已登录（Session中是否有userId）
 * 2. 已登录则将userId存入UserContext（ThreadLocal）
 * 3. 未登录则返回401错误JSON
 * 4. 记录请求日志
 * 5. 请求结束后清除UserContext
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String requestInfo = String.format("[%s] %s %s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getMethod(),
                request.getRequestURI());
        log.debug("拦截器捕获请求: {}", requestInfo);

        HttpSession session = request.getSession(false);
        Long userId = null;
        if (session != null) {
            userId = (Long) session.getAttribute("userId");
        }

        if (userId == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            Result<Void> errorResult = Result.error(401, "未登录，请先登录");
            response.getWriter().write(OBJECT_MAPPER.writeValueAsString(errorResult));

            log.warn("请求被拦截（未登录）: {}", requestInfo);
            return false;
        }

        UserContext.setUserId(userId);
        log.debug("用户 {} 已登录，放行请求", userId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        UserContext.clear();

        if (ex != null) {
            log.error("请求处理异常: {} {}, 异常: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    ex.getMessage());
        }
    }
}