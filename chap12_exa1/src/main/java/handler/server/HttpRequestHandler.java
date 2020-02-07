package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author SuanCaiYv
 * @time 2020/2/7 下午9:26
 */
public class HttpRequestHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        if (msg instanceof FullHttpRequest) {
            dealHttpRequest(ctx, (FullHttpRequest) msg);
        }
        else {
            ctx.fireChannelRead(msg);
        }
    }
    public void dealHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request)
    {
        // do something...
    }
}
