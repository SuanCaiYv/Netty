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
        ctx.write(Unpooled.copiedBuffer("123".getBytes()));
    }
}
