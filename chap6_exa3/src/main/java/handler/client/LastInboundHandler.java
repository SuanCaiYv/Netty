package handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author joker
 * @time 2020/1/12 下午4:37
 */
public class LastInboundHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        long startTime = System.currentTimeMillis();
        System.out.println(startTime+": "+cause.getMessage());
    }
}
