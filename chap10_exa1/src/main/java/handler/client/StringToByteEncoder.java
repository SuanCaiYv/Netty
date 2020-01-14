package handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码操作, 属于出站操作, 泛型是from
 * @author SuanCaiYv
 * @time 2020/1/15 上午12:03
 */
public class StringToByteEncoder extends MessageToByteEncoder<String>
{
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception
    {
        System.out.println("String转换成Byte");
        out.writeBytes(msg.getBytes());
    }
}
