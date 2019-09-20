package com.tiddar.minidb.core.api.request;

import lombok.Data;

import java.util.Map;

/**
 * @author zhangweichen
 * @date 2019/9/12 11:09 上午
 */
@Data
public class InsertRequest extends Request {
    String table;
    Map<String,String> values;
}
