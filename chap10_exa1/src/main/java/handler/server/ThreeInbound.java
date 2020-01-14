package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 上午12:33
 */
public class ThreeInbound extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        Integer num = 789;
        ctx.write(num);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println("读到了Integer: "+msg);
    }
}
