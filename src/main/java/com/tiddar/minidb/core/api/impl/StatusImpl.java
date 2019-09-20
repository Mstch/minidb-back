package com.tiddar.minidb.core.api.impl;

import com.tiddar.minidb.core.api.Status;
import com.tiddar.minidb.core.data.Tables;
import com.tiddar.minidb.core.model.Table;

import java.util.Map;

/**
 * @author zhangweichen
 * @date 2019/9/11 3:14 下午
 */
public class StatusImpl implements Status {

    public static final Status instance = new StatusImpl();


    @Override
    public Map<String, Table> showTables() {
        return Tables.tables;
    }
}
