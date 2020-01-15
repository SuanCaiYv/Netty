package handler.client;

import io.netty.channel.*;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午10:42
 */
public class LastOut extends ChannelOutboundHandlerAdapter
{
    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        ctx.flush();
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
    {
        ctx.channel().closeFuture();
        promise.addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                System.out.println("关闭失败");
            }
        });
    }
}
