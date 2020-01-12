package handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author joker
 * @time 2020/1/12 下午4:38
 */
public class LastOutboundHandler extends ChannelOutboundHandlerAdapter
{
    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
    {
        super.close(ctx, promise);
        System.out.println("连接关闭");
    }
}
