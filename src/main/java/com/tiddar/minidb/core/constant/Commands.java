package com.tiddar.minidb.core.constant;

import com.tiddar.minidb.core.api.impl.DDLImpl;
import com.tiddar.minidb.core.api.request.CreateTableRequest;
import com.tiddar.minidb.core.api.request.RenameRequest;

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
    static{
        commandSet.add("create");
        commandSet.add("drop");
        commandSet.add("rename");
        commandSet.add("show");
        commandMap.put("create",request-> DDLImpl.instance.create((CreateTableRequest) request));
        commandMap.put("drop",request-> DDLImpl.instance.drop((String) request));
        commandMap.put("rename",request-> DDLImpl.instance.rename((RenameRequest) request));
    }
}
