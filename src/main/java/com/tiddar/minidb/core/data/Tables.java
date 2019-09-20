package com.tiddar.minidb.core.data;

import com.tiddar.minidb.core.model.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangweichen
 * @date 2019/9/6 11:41 上午
 */
public class Tables {
    public static Map<String, Table> tables = new HashMap<>();

    public static int add(String name, Table table) {
        if (tables.containsKey(name)) {
            return 0;
        }
        synchronized (tables) {
            if (tables.containsKey(name)) {
                return 0;
            } else {
                tables.put(name, table);
                return 1;
            }
        }
    }

    public static int remove(String name) {
        if (!tables.containsKey(name)) {
            return 0;
        }
        synchronized (tables) {
            if (!tables.containsKey(name)) {
                return 0;
            } else {
                tables.remove(name);
                return 1;
            }
        }
    }

    public static int rename(String name, String newName) {
        synchronized (tables) {
            if (add(newName, tables.get(name)) == 1) {
                return remove(name);
            }
        }
        return 0;
    }
}
