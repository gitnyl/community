package com.nylee.api.common.advice;

import com.nylee.api.common.repository.model.JwtToken;
import com.nylee.api.common.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@ControllerAdvice
public class HttpResponseAdvice  implements ResponseBodyAdvice<Object> {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean supports(
            MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType
    ) {

        return true;
    }

    @Override
    public Object beforeBodyWrite(

            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {

        if ( !request.getURI().toString().contains("login") && request.getURI().toString().contains("/mcks/admin") ) {
            Map<String,Object> info = jwtService.get("admin");
            String id = (String)info.get("id");
            Integer idx = (Integer)info.get("idx");

            JwtToken jwtData = new JwtToken();
            jwtData.setId(id);
            jwtData.setIdx(idx);

            String jwtToken = jwtService.resignToken("admin", jwtData, "adminInfo");

            // HTTP 요청 처리 후 응답을 가공하는 로직 작성
            response.getHeaders().add("expire_token", jwtToken);
        }

        return body;
    }
}
