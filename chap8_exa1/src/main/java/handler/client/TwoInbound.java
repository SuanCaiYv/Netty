package handler.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author joker
 * @date 2020/1/13 下午8:09
 */
public class TwoInbound extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        ctx.write(Unpooled.copiedBuffer("another text".getBytes()));
    }
}
