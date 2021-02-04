package com.javabom.bomplatform.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.SerializationUtils;

@UtilityClass
public class ModelMapperUtils {

    public static <T> T deserialize(byte[] data, Class<T> tClass) {
        return (tClass.cast(SerializationUtils.deserialize(data)));
    }

    public static byte[] serialize(Object data) {
        return SerializationUtils.serialize(data);
    }
}
