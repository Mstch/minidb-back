package com.tiddar.minidb.core.server;

/**
 * @author zhangweichen
 * @date 2019/9/11 3:55 下午
 */
public enum CommandTypeEnum {
    STATUS,
    INSERT, SELECT, DELETE, UPDATE,
    CREATE, DROP, RENAME,
    TRANSACTION;
}
