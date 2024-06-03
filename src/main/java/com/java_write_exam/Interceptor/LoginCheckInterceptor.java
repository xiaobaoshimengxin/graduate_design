package com.java_write_exam.Interceptor;

import com.java_write_exam.DTO.UserClaim;
import com.java_write_exam.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader("Authorization");
        try {
            if (StringUtils.hasLength(jwt)){
                Claims claims = JwtUtil.parseJWT(jwt);
                UserClaim.id = (Integer) claims.get("id");
                UserClaim.account = (String) claims.get("account");
                UserClaim.name = (String) claims.get("name");
                UserClaim.exp = (Integer) claims.get("exp");
                return true;
            }else {
                response.setStatus(401);
                return false;
            }
        } catch (Exception e){
            log.info("没有令牌或令牌无效");
            e.printStackTrace();
            response.setStatus(401);
            return false;
        }
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//    }
}
