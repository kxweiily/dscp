package com.topideal.dscp.common.fieldDES;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.IOException;
import java.util.Objects;

/**
 * jackson 自定义序列化处理 -- 脱敏
 * (已废弃) 因此方式没法获知用户数据权限 进而判断
 * @Author: kongxj
 * @Date: 2022/8/2 14:35
 */
public class SensitiveJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * Singleton instance to use.
     */
    public final static ToStringSerializer instance = new ToStringSerializer();

    private DataMaskingType dmType;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(DataMaskingUtils.handleDate(value, dmType));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        FieldDES annotation = property.getAnnotation(FieldDES.class);
        if (Objects.nonNull(annotation) && Objects.equals(String.class, property.getType().getRawClass())) {
            this.dmType = annotation.dmType();
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}

