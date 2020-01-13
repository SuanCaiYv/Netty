package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 * @author joker
 * @date 2020/1/13 上午12:18
 */
public class TwoOutboundHandler extends ChannelOutboundHandlerAdapter
{
    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        ctx.flush();
    }
}
