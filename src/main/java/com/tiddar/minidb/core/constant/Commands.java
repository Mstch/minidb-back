package com.tiddar.minidb.core.constant;

import com.tiddar.minidb.core.api.impl.DDLImpl;
import com.tiddar.minidb.core.api.impl.DMLImpl;
import com.tiddar.minidb.core.api.request.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author zhangweichen
 * @date 2019/9/9 5:24 下午
 */
public class Commands {
    public static Set<String> commandSet = new HashSet<>();
    public static Map<String, Function> commandMap = new HashMap<>();

    static {
        commandSet.add("create");
        commandSet.add("drop");
        commandSet.add("rename");
        commandSet.add("show");
        commandMap.put("create", request -> DDLImpl.instance.create((CreateTableRequest) request));
        commandMap.put("drop", request -> DDLImpl.instance.drop((DropRequest) request));
        commandMap.put("rename", request -> DDLImpl.instance.rename((RenameRequest) request));
        commandMap.put("insert", request -> DMLImpl.instance.insert((InsertRequest) request));
        commandMap.put("select", request -> DMLImpl.instance.select((SelectRequest) request));
        commandMap.put("delete", request -> DMLImpl.instance.delete((SelectRequest) request));
        commandMap.put("update", request -> DMLImpl.instance.update((UpdateRequest) request));
    }
}
