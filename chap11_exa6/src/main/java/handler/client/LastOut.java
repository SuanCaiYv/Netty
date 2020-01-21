package handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author SuanCaiYv
 * @time 2020/1/20 下午9:32
 */
public class LastOut extends ChannelOutboundHandlerAdapter
{
    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        ctx.flush();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        ctx.writeAndFlush(msg, promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
    {
        ctx.channel().closeFuture();
        ctx.close();
    }

}
