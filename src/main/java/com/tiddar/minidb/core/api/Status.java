package com.tiddar.minidb.core.api;

import com.tiddar.minidb.core.model.Table;

import java.util.Map;

/**
 * @author zhangweichen
 * @date 2019/9/11 3:13 下午
 */
public interface Status {

    /**
     * 查询表
     * @return 所有表
     */
    Map<String, Table> showTables();
}
