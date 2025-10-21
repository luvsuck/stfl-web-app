package com.luvsic.stflserverapp.custom.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luvsic.stflserverapp.custom.R;
import com.luvsic.stflserverapp.custom.util.JacksonUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 18:38
 * @description
 */
@RestControllerAdvice(basePackages = {"com.luvsic"})
public class GlobalAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        Type type = returnType.getGenericParameterType();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            if (rawType.equals(R.class)) return false;
        }
        return !returnType.getGenericParameterType().equals(R.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper om = new ObjectMapper();
            return JacksonUtil.tryParse(() -> JacksonUtil.getObjectMapper().writeValueAsString(new R<>(body)));
//            return JSON.toJSONString(new R<>(body), SerializerFeature.WriteDateUseDateFormat);
        }
        return new R<>(body);
    }
}
