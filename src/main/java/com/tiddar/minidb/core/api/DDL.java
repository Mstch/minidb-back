package com.tiddar.minidb.core.api;

import com.tiddar.minidb.core.api.request.CreateTableRequest;
import com.tiddar.minidb.core.api.request.DropRequest;
import com.tiddar.minidb.core.api.request.RenameRequest;
import com.tiddar.minidb.core.model.Table;

import java.util.Map;

/**
 * @author zhangweichen
 * @date 2019/9/6 11:37 上午
 */
public interface DDL {

    /**
     * 建表
     * @return
     */
    int create(CreateTableRequest request);

    /**
     * 删除表
     * @return
     */
    int drop(DropRequest request);

    /**
     * 改名
     * @return
     */
    int rename(RenameRequest renameRequest);

}
