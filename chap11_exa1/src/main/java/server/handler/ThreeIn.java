package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午7:41
 */
public class ThreeIn extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        HttpResponse response = (HttpResponse) msg;
        response.setProtocolVersion(HttpVersion.HTTP_1_1);
        response.setStatus(HttpResponseStatus.ACCEPTED);
        ctx.write(response);
    }
}
