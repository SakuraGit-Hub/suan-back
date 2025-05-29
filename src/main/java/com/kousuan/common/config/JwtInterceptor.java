package com.kousuan.common.config;


import com.kousuan.common.util.JwtUtil;
import com.kousuan.common.util.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * @author ljx
 * jwt拦截器
 */

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = jwtUtil.getTokenFromRequest(request);

        try {
            if (token == null) {
                sendError(response, 401, "请提供Token");
                return false;
            }

            // 解析Token获取用户ID
            String userId = jwtUtil.parseToken(token);
            if (userId == null) {
                sendError(response, 401, "无效的Token");
                return false;
            }

            // 将用户ID存入请求属性
            request.setAttribute("userId", userId);
            return true;

        } catch (ExpiredJwtException e) {
            sendError(response, 401, "Token已过期");
            return false;
        } catch (JwtException e) {
            sendError(response, 401, "无效的Token");
            return false;
        }
    }

    private void sendError(HttpServletResponse response, int code, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(code);
        response.getWriter().write(Result.fail(code, message).toString());
    }

}
