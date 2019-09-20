package com.tiddar.minidb.core.model;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhangweichen
 * @date 2019/9/12 11:14 上午
 */
public class Row {
    public List<Col> cols = new ArrayList<>();
    public List<Value> values = new ArrayList<>();
    public ReentrantReadWriteLock.ReadLock readLock;
    public ReentrantReadWriteLock.WriteLock writeLock;
    public Long lockTransactionId;
}
