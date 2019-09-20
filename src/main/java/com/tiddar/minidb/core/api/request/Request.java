package com.tiddar.minidb.core.api.request;

import io.netty.channel.Channel;
import lombok.Data;


/**
 * @author zhangweichen
 * @date 2019/9/20 2:06 下午
 */
@Data
public class Request {
    Channel channel;
}
