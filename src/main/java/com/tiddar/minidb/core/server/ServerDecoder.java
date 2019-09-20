package com.tiddar.minidb.core.server;

import com.tiddar.minidb.core.api.Status;
import com.tiddar.minidb.core.api.impl.StatusImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Marker;

import java.util.List;


/**
 * @author zhangweichen
 * @date 2019/9/11 3:18 下午
 */
@Log4j2
public class ServerDecoder extends MessageToMessageDecoder<ByteBuf> {

    private Status status = StatusImpl.instance;
    private static final String STATUS_COMMAND = "status";

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        String source = StringUtils.chomp(msg.toString(CharsetUtil.UTF_8));
        String[] statusCommand = source.split(" ");
        try {
            if (STATUS_COMMAND.equals(statusCommand[0])) {
                Command command = new Command();
                command.typeEnum = CommandTypeEnum.STATUS;
                command.content = source.split(" ", 2)[1];
                out.add(command);
            }
        } catch (Exception e) {
            String error = "命令:" + source + "解析异常:" + e.fillInStackTrace();
            log.error(error);
            ctxWrite(ctx, ctx.alloc().buffer(), error);
        }
    }


    public void ctxWrite(ChannelHandlerContext ctx, ByteBuf buf, String msg) {
        buf.writeCharSequence(msg + "\n\r", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

}
