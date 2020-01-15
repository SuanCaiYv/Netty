package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 上午10:56
 */
public class LastIn extends ChannelInboundHandlerAdapter
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        System.out.println(cause.getMessage());
        ctx.channel().closeFuture().sync();
    }
}
