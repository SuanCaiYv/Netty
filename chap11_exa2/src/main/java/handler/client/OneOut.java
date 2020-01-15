package handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午7:55
 */
public class OneOut extends ChannelOutboundHandlerAdapter
{
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        ctx.writeAndFlush(msg, promise);
    }
}
