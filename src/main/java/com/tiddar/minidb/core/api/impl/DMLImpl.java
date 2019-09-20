package com.tiddar.minidb.core.api.impl;

import com.google.gson.Gson;
import com.tiddar.minidb.core.api.DDL;
import com.tiddar.minidb.core.api.DML;
import com.tiddar.minidb.core.api.request.InsertRequest;
import com.tiddar.minidb.core.api.request.Request;
import com.tiddar.minidb.core.api.request.SelectRequest;
import com.tiddar.minidb.core.api.request.UpdateRequest;
import com.tiddar.minidb.core.data.Data;
import com.tiddar.minidb.core.data.Tables;
import com.tiddar.minidb.core.model.Result;
import com.tiddar.minidb.core.model.Table;
import com.tiddar.minidb.core.sync.SyncClient;

/**
 * @author zhangweichen
 * @date 2019/9/12 11:09 上午
 */
public class DMLImpl implements DML {

    public static final DML instance = new DMLImpl();
    private static Gson gson = new Gson();

    @Override
    public int insert(InsertRequest request) {
        Table table = Tables.tables.get(request.getTable());
        if (table == null) {
            return 0;
        }
        int result = Data.insert(table, request.getValues(), request.getChannel());
        if (result == 1) {
            SyncClient.syncAll("insert", gson.toJson(request));
        }
        return 0;
    }

    @Override
    public Result select(SelectRequest request) {
        return null;
    }

    @Override
    public int update(UpdateRequest request) {
        return 0;
    }

    @Override
    public int delete(SelectRequest request) {
        return 0;
    }

}
