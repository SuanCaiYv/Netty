package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午7:18
 */
public class ThreeIn extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        ctx.write("text");
    }
}
