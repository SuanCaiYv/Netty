package handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 * @author joker
 * @date 2020/1/12 下午11:46
 */
public class TwoOutboundHandler extends ChannelOutboundHandlerAdapter
{
    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        ctx.flush();
    }
}
