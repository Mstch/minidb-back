package com.tiddar.minidb.core.api.request;

import com.tiddar.minidb.core.model.Col;
import com.tiddar.minidb.core.model.Value;
import lombok.Data;

import java.util.List;

/**
 * @author zhangweichen
 * @date 2019/9/12 3:57 下午
 */
@Data
public class SelectRequest {
    private String table;

    private List<Where> conditions;

    @Data
    class Where {
        private Col col;
        private RelationEnum relation;
        private Value value;
        private Value[] values;
    }
}
