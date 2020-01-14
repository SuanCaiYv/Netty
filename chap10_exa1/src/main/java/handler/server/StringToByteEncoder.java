package handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 上午12:58
 */
public class StringToByteEncoder extends MessageToByteEncoder<String>
{
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception
    {
        out.writeBytes(msg.getBytes());
    }
}
