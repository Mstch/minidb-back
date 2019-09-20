package com.tiddar.minidb.core.sync;

import com.google.gson.Gson;
import com.tiddar.minidb.core.api.DDL;
import com.tiddar.minidb.core.api.impl.DDLImpl;
import com.tiddar.minidb.core.api.request.CreateTableRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


/**
 * @author zhangweichen
 * @date 2019/9/6 5:10 下午
 */
public class SyncHandler extends SimpleChannelInboundHandler<SyncCommand> {

    private Gson gson = new Gson();
    private DDL ddl = DDLImpl.instance;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SyncCommand syncCommand) throws Exception {
        String id = syncCommand.id;
        switch (syncCommand.command.toLowerCase()) {
            case "create": {
                int result = doCreate(syncCommand);
                if (result == 0) {
                    ctxWrite(channelHandlerContext, channelHandlerContext.alloc().buffer(), id + " 0");
                } else {
                    ctxWrite(channelHandlerContext, channelHandlerContext.alloc().buffer(), id + " 1");
                }
                break;
            }
            case "drop": {
                int result = doDrop(syncCommand);
                if (result == 0) {
                    ctxWrite(channelHandlerContext, channelHandlerContext.alloc().buffer(), id + " 0");
                } else {
                    ctxWrite(channelHandlerContext, channelHandlerContext.alloc().buffer(), id + " 1");
                }
            }
            break;
            default:
                break;
        }
    }

    private int doCreate(SyncCommand syncCommand) {
        return ddl.create(gson.fromJson(syncCommand.content, CreateTableRequest.class));
    }

    private int doDrop(SyncCommand syncCommand) {
        return ddl.drop(syncCommand.content);
    }

    public void ctxWrite(ChannelHandlerContext ctx, ByteBuf buf, String msg) {
        buf.writeCharSequence(msg + "\n\r", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

}