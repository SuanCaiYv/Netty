package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author SuanCaiYv
 * @time 2020/2/8 上午12:14
 */
public class LastOut extends ChannelOutboundHandlerAdapter
{
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        ctx.writeAndFlush(msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        ctx.flush();
    }
}
