package com.tiddar.minidb.core.model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tiddar.minidb.core.type.TypeEnum;
import lombok.Data;

import java.util.Map;

/**
 * @author zhangweichen
 * @date 2019/9/12 11:01 上午
 */
public class Value<T> {
    /**
     * 落地值
     */
    public T value;
    public Map<Long, T> values;

    public static Value fromString(Col col, String v) {
        if (col.getTypeEnum().equals(TypeEnum.INT)) {
            Value<Integer> result = new Value<>();
            result.value = Integer.parseInt(v);
            return result;
        } else if (col.getTypeEnum().equals(TypeEnum.BIGINT)) {
            Value<Long> result = new Value<>();
            result.value = Long.parseLong(v);
            return result;
        } else if (col.getTypeEnum().equals(TypeEnum.VCHAR)) {
            Value<String> result = new Value<>();
            result.value = v;
            return result;
        } else if (col.getTypeEnum().equals(TypeEnum.BOOL)) {
            Value<Boolean> result = new Value<>();
            result.value = Boolean.valueOf(v.toLowerCase());
            return result;
        } else if (col.getTypeEnum().equals(TypeEnum.FLOAT)) {
            Value<Float> result = new Value<>();
            result.value = Float.parseFloat(v);
            return result;
        }
        return null;
    }
}
