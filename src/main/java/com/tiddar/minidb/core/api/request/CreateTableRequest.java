package com.tiddar.minidb.core.api.request;

import com.tiddar.minidb.core.model.Col;
import lombok.Data;

import java.util.List;

/**
 * @author zhangweichen
 * @date 2019/9/6 6:23 下午
 */
@Data
public class CreateTableRequest {
    private String name;
    private List<Col> columns;
    private Col key;
}
