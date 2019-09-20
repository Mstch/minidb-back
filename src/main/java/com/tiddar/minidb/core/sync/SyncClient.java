package com.tiddar.minidb.core.sync;

import com.tiddar.minidb.core.config.Node;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import lombok.extern.log4j.Log4j2;

import java.util.*;

/**
 * @author zhangweichen
 * @date 2019/9/6 3:57 下午
 */
@Log4j2
public class SyncClient {
    private static Node instance = Node.getInstance();
    private static NioEventLoopGroup worker = new NioEventLoopGroup();
    private static Bootstrap bootstrap = new Bootstrap();
    private static List<Channel> channels = new ArrayList<>();
    private static final AttributeKey<Node> NODE_KEY = AttributeKey.newInstance("node");

    public static void init() throws InterruptedException {
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new LoggingHandler(LogLevel.INFO));

        for (Node slave : instance.slaves) {
            ChannelFuture future = bootstrap
                    .connect(slave.host, slave.syncPort)
                    .addListener(f -> log.info("成功连接到slave  {}:{}", slave.host, slave.syncPort))
                    .sync();
            future.channel().attr(NODE_KEY).set(slave);
            channels.add(future.channel());
        }


    }

    public static void sync(String command, String content, Channel channel) {
        String id = UUID.randomUUID().toString().replace("-", "");
        String message = id + " " + command + " " + content;
        log.info("同步到{}消息:{}", channel.attr(NODE_KEY).get(), message);
        channelWrite(channel, channel.alloc().buffer(), message);
    }

    public static void syncAll(String command, String content) {
        for (Channel channel : channels) {
            sync(command, content, channel);
        }
    }

    private static void channelWrite(Channel channel, ByteBuf buf, String msg) {
        buf.writeCharSequence(msg + "\n\r", CharsetUtil.UTF_8);
        channel.writeAndFlush(buf);
    }


    class Job {
        int status;
        String id;
        Channel channel;
        String command;
        String content;

        public Job(int status, String id, Channel channel, String command, String content) {
            this.status = status;
            this.id = id;
            this.channel = channel;
            this.command = command;
            this.content = content;
        }
    }

    class Retry {
        String id;
        Job job;
        Channel channel;
        int times = 0;

        public Retry(String id, Job job, Channel channel, int times) {
            this.id = id;
            this.job = job;
            this.channel = channel;
            this.times = times;
        }
    }


}
