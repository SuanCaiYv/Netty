package server.handler;

import io.netty.channel.*;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 上午11:28
 */
public class LastOut extends ChannelOutboundHandlerAdapter
{
    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
    {
        ctx.channel().closeFuture().sync();
        promise.addListener(new ChannelFutureListener()
        {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception
            {
                if (!future.isSuccess()) {
                    System.out.println("未能正常关闭channel");
                }
            }
        });
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        ctx.flush();
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
    {
        System.out.println("关闭连接");
    }
}
