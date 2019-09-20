package com.tiddar.minidb.core.data;

import com.tiddar.minidb.core.index.BPlusTree;
import com.tiddar.minidb.core.model.*;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tiddar.minidb.core.server.ServerHandler.SESSION_KEY;

/**
 * @author zhangweichen
 * @date 2019/9/6 11:42 上午
 */
public class Data {
    public static final Map<Table, BPlusTree<Long, Row>> datas = new HashMap<>();


    public static int insert(Table table, Map<String, String> values, Channel channel) {
        BPlusTree<Long, Row> tree = datas.get(table);
        Row row = new Row();
        values.forEach((k, v) -> {
            Col col = table.getCol(k);
            Value value = Value.fromString(col, v);
            row.cols.add(col);
            row.values.add(value);
        });
        if (channel.attr(SESSION_KEY).get().get("transaction") != null) {
            row.writeLock.lock();
        }
        if (tree.search(Long.valueOf(values.get("id"))) != null) {
            return 0;
        }
        tree.insert(Long.valueOf(values.get("id")), row);
        return 1;
    }


}
