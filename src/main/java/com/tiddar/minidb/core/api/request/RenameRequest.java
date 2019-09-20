package com.tiddar.minidb.core.api.request;

import lombok.Data;

/**
 * @author zhangweichen
 * @date 2019/9/19 10:45 上午
 */
@Data
public class RenameRequest {
    String oldName;
    String newName;
}
