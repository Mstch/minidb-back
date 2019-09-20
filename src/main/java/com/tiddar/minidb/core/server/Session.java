package com.tiddar.minidb.core.server;

import java.nio.channels.Channel;
import java.util.Map;

/**
 * @author zhangweichen
 * @date 2019/9/19 10:54 上午
 */
public class Session {
    String id;
    Channel channel;

    Map<String,Object> attr;
}
