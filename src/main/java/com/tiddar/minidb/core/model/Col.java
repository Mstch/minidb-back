package com.tiddar.minidb.core.model;

import com.tiddar.minidb.core.type.TypeEnum;
import lombok.Data;

/**
 * @author zhangweichen
 * @date 2019/9/6 11:34 上午
 */
@Data
public class Col {
    String name;
    TypeEnum typeEnum;
    Integer length;
    Boolean auto;
}
