package com.tiddar.minidb.core.sync;

import com.tiddar.minidb.core.config.Node;
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
 * @date 2019/9/6 3:56 下午
 */
@Log4j2
public class SyncServer {

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
                        nioSocketChannel.pipeline().addLast(new SyncDecoder()).addLast(new SyncHandler());
                    }
                })
                .bind(instance.syncPort)
                .addListener((ChannelFutureListener) future -> log.info("sync服务启动成功!"));
    }


}
