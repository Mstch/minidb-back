package com.tiddar.minidb.core.api.impl;

import com.google.gson.Gson;
import com.tiddar.minidb.core.api.DDL;
import com.tiddar.minidb.core.api.request.CreateTableRequest;
import com.tiddar.minidb.core.api.request.RenameRequest;
import com.tiddar.minidb.core.config.Node;
import com.tiddar.minidb.core.config.NodeRoleEnum;
import com.tiddar.minidb.core.data.Data;
import com.tiddar.minidb.core.data.Tables;
import com.tiddar.minidb.core.index.BPlusTree;
import com.tiddar.minidb.core.model.Col;
import com.tiddar.minidb.core.model.Table;
import com.tiddar.minidb.core.sync.SyncClient;
import com.tiddar.minidb.core.type.TypeEnum;
import lombok.extern.log4j.Log4j2;

/**
 * @author zhangweichen
 * @date 2019/9/6 11:41 上午
 */
@Log4j2
public class DDLImpl implements DDL {

    public static final DDL instance = new DDLImpl();
    private static final Gson gson = new Gson();
    private static final Node node = Node.getInstance();

    @Override
    public int create(CreateTableRequest request) {
        Table table = new Table();
        table.setName(request.getName());
        table.setKey(request.getKey());
        table.setColumns(request.getColumns());
        int result = Tables.add(table.getName(), table);
        if (result == 1) {
            Data.datas.put(table, new BPlusTree<>(5));
            if (node.role.equals(NodeRoleEnum.MASTER)) {
                SyncClient.syncAll("create", gson.toJson(request));
            }
            log.info("新增table:{}", table.getName());
            return 1;
        }
        log.info("新增table{}失败,表名重复", table.getName());
        return 0;
    }

    @Override
    public int drop(String name) {
        return Tables.remove(name);
    }

    @Override
    public int rename(RenameRequest renameRequest) {
        return 0;
    }

}
