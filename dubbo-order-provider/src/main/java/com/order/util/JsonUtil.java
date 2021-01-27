package com.order.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author :
 * @date 2021-01-04 15:21
 * @Since V1.0.0
 * @description
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    // 日起格式化
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 序列化
     *
     * @param obj
     * @return
     */
    public static String toJsonStr(Object obj) {
        try {
            String jsonStr = objectMapper.writeValueAsString(obj);
            return jsonStr;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 简单对象反序列化
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        try {
            final T value = objectMapper.readValue(jsonStr, clazz);
            return value;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 复杂对象反序列化
     *
     * @param jsonStr
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String jsonStr, TypeReference<T> type) {
        try {
            T value = objectMapper.readValue(jsonStr, type);
            return value;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * map转为对象
     *
     * @param map
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T map2Obj(Map map, Class<T> type) {
        try {
            String jsonStr = objectMapper.writeValueAsString(map);
            final T value = objectMapper.readValue(jsonStr, type);
            return value;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * map转换为复杂对象
     *
     * @param map
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T map2Obj(Map map, TypeReference<T> type) {
        try {
            String jsonStr = objectMapper.writeValueAsString(map);
            final T value = objectMapper.readValue(jsonStr, type);
            return value;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转为map
     *
     * @param object
     * @return
     */
    public static Map<String, Object> obj2Map(Object object) {
        try {
            String jsonStr = objectMapper.writeValueAsString(object);
            final Map<String, Object> map = objectMapper.readValue(jsonStr, Map.class);
            return map;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
