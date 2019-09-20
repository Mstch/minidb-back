package com.tiddar.minidb.core.server;

import com.tiddar.minidb.core.config.Node;
import com.tiddar.minidb.core.sync.SyncDecoder;
import com.tiddar.minidb.core.sync.SyncHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.log4j.Log4j2;

/**
 * @author zhangweichen
 * @date 2019/9/11 3:15 下午
 */
@Log4j2
public class Server {

    private static Node instance = Node.getInstance();

    private static NioEventLoopGroup boss = new NioEventLoopGroup();
    private static NioEventLoopGroup worker = new NioEventLoopGroup();
    private static ServerBootstrap bootstrap = new ServerBootstrap();

    public static void start() {
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new ServerDecoder()).addLast(new ServerHandler());
                    }
                })
                .bind(instance.port)
                .addListener((ChannelFutureListener) future -> log.info("主服务启动成功!"));
    }

}
