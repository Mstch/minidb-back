package com.tiddar.minidb.core.transaction;


import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhangweichen
 * @date 2019/9/19 2:31 下午
 */
public class Transactions {
    public static AtomicLong incrId = new AtomicLong(1);
    public static Map<Long, Channel> transactionMap = new HashMap<>();


    public static Long begin(Channel channel) {
        Long id = incrId.incrementAndGet();
        transactionMap.put(id, channel);
        return id;
    }

    public static void commit(Long id) {
        transactionMap.remove(id);
    }



}
