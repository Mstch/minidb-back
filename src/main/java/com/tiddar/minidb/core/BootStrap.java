package com.tiddar.minidb.core;

import com.tiddar.minidb.core.api.impl.DDLImpl;
import com.tiddar.minidb.core.api.request.CreateTableRequest;
import com.tiddar.minidb.core.config.Node;
import com.tiddar.minidb.core.config.NodeRoleEnum;
import com.tiddar.minidb.core.model.Col;
import com.tiddar.minidb.core.server.Server;
import com.tiddar.minidb.core.sync.SyncClient;
import com.tiddar.minidb.core.sync.SyncServer;
import com.tiddar.minidb.core.type.TypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangweichen
 * @date 2019/9/6 4:40 下午
 */
public class BootStrap {
    private static Node instance = Node.getInstance();

    public static void main(String[] args) throws InterruptedException {
        if (instance.role.equals(NodeRoleEnum.MASTER)) {
            SyncClient.init();
            CreateTableRequest request = new CreateTableRequest();
            request.setName("fuck");
            Col col = new Col();
            col.setLength(20);
            col.setName("id");
            col.setTypeEnum(TypeEnum.BIGINT);
            request.setKey(col);
            List<Col> cols = new ArrayList<>();
            cols.add(col);
            request.setColumns(cols);
            new DDLImpl().create(request);
        } else {
            SyncServer.start();
        }
        Server.start();
    }
}
