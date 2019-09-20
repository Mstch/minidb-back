package com.tiddar.minidb.core.sync;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;

/**
 * @author zhangweichen
 * @date 2019/9/10 10:47 上午
 */
@Log4j2
public class SyncClientHandler extends SimpleChannelInboundHandler<ByteBuf> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("收到消息:{}",msg.readCharSequence(msg.capacity(), StandardCharsets.UTF_8));
    }
}
