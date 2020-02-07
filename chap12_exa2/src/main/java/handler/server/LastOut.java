package handler.server;

import io.netty.channel.*;

/**
 * @author SuanCaiYv
 * @time 2020/2/5 下午11:27
 */
public class LastOut extends ChannelOutboundHandlerAdapter
{
    /**
     * ctx.channel().writeAndFlush()会触发此方法
     * @param ctx NA
     * @param msg NA
     * @param promise NA
     * @throws Exception NA
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        System.out.println("写数据");
        ctx.writeAndFlush(msg, promise);
    }

    /**
     * ctx.channel().flush()会触发此方法
     * @param ctx NA
     * @throws Exception NA
     */
    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("冲刷数据");
        ctx.flush();
    }
}
