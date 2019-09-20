package com.tiddar.minidb.core.sync;

import com.tiddar.minidb.core.constant.Commands;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


/**
 * @author zhangweichen
 * @date 2019/9/6 11:04 下午
 */
@Log4j2
public class SyncDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        String msg = StringUtils.chomp(byteBuf.toString(CharsetUtil.UTF_8));
        log.info("收到来自{}的同步消息:{}", channelHandlerContext.channel().remoteAddress(), msg);
        String[] command = msg.split(" ", 4);
        if (command.length != 3 || !Commands.commandSet.contains(command[1])) {
            ByteBuf buf = channelHandlerContext.alloc().buffer();
            ctxWrite(channelHandlerContext, buf, "命令非法!");
            return;
        }
        SyncCommand syncCommand = new SyncCommand();
        syncCommand.id = command[0];
        syncCommand.command = command[1];
        syncCommand.content = command[2];
        list.add(syncCommand);
    }

    public void ctxWrite(ChannelHandlerContext ctx, ByteBuf buf, String msg) {
        buf.writeCharSequence(msg + "\n\r", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

}
