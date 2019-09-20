package com.tiddar.minidb.core.server;

import com.google.gson.Gson;
import com.tiddar.minidb.core.api.Status;
import com.tiddar.minidb.core.api.impl.StatusImpl;
import com.tiddar.minidb.core.transaction.Transactions;
import com.tiddar.minidb.core.type.TypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhangweichen
 * @date 2019/9/11 4:08 下午
 */
public class ServerHandler extends SimpleChannelInboundHandler<Command> {
    private Status status = StatusImpl.instance;
    private static final Gson gson = new Gson();
    public static final AttributeKey<Map<String, Object>> SESSION_KEY = AttributeKey.newInstance("session");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command msg) throws Exception {
        if (ctx.channel().attr(SESSION_KEY).get() == null) {
            ctx.channel().attr(SESSION_KEY).set(new HashMap<>(2));
            ctx.channel().attr(SESSION_KEY).get().put("transaction", false);
        }
        Map<String, Object> attrs = ctx.channel().attr(SESSION_KEY).get();

        //开启事务


        switch (msg.typeEnum) {
            case STATUS:
                if ("show tables".equals(msg.content)) {
                    ctxWrite(ctx, ctx.alloc().buffer(), gson.toJson(status.showTables()));
                }
                break;
            case DML:
                break;
            case TRANSACTION: {
                if ("begin".equals(msg.content)) {
                    if (attrs.get("transaction") != null) {
                        ctxWrite(ctx, ctx.alloc().buffer(), "已开启事务,当前事务id:" + attrs.get("transaction"));
                        return;
                    }
                    attrs.put("transaction", Transactions.begin(ctx.channel()));
                }
                if ("commit".equals(msg.content)) {
                    if (attrs.get("transaction") == null) {
                        ctxWrite(ctx, ctx.alloc().buffer(), "当前会话未开启事务，提交个🔨");
                        return;
                    }
                    Transactions.commit((Long) attrs.get("transaction"));
                    attrs.remove("transaction");
                }
            }
            break;
            default:
                break;
        }
    }


    public void ctxWrite(ChannelHandlerContext ctx, ByteBuf buf, String msg) {
        buf.writeCharSequence(msg + "\n\r", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

}
