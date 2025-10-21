package com.luvsic.stflserverapp.custom.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.json.JsonParseException;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 18:40
 * @description
 */
public class JacksonUtil {
    private final static ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static <T> T tryParse(Callable<T> parser) {
        return tryParse(parser, JacksonException.class);
    }

    public static <T> T tryParse(Callable<T> parser, Class<? extends Exception> check) {
        try {
            return parser.call();
        } catch (Exception e) {
            if (check.isAssignableFrom(e.getClass())) {
                throw new JsonParseException(e);
            }
            throw new IllegalStateException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValueFromMap(Map<String, ?> map, String key) {
        return (T) map.get(key);
    }

}