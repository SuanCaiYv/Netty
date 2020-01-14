package handler.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author joker
 * @date 2020/1/14 下午4:22
 */
public class OneIn extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        int num = 123;
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((num >> 24) & 0xFF);
        bytes[1] = (byte) ((num >> 16) & 0xFF);
        bytes[2] = (byte) ((num >> 8) & 0xFF);
        bytes[3] = (byte) ((num) & 0xFF);
        ctx.write(Unpooled.copiedBuffer(bytes));
    }
}
