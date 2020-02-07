package handler.server;

import io.netty.channel.*;

/**
 * @author SuanCaiYv
 * @time 2020/2/5 下午11:27
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
