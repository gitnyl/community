package com.nylee.api.common.interceptor;

import com.nylee.api.common.service.JwtService;
import com.nylee.common.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
    private static final String HEADER_AUTH = "Authorization";

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String token = request.getHeader(HEADER_AUTH);

        try {
            if( token != null && jwtService.isUsable(token,"admin")) {
                request.setAttribute("start",System.currentTimeMillis());
                return true;
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        } catch (UnauthorizedException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Map<String,Object> info = jwtService.get("admin");
        String id = (String)info.get("id");
        Long end = System.currentTimeMillis();
        Long start = Long.parseLong(request.getAttribute("start").toString());

        logger.info(String.format("[MCK_ADMIN_API_STAT] ::: [ID] : %s | [RESPONSE STATUS] : %s | [REQUEST URI] : %s | [ResponseTime] : %d", id, response.getStatus(), request.getRequestURI(),(end-start)));
    }
}
