package handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author SuanCaiYv
 * @time 2020/1/14 下午5:39
 */
public class TwoIn extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        Integer num = 12;
        ctx.write(num);
    }
}
