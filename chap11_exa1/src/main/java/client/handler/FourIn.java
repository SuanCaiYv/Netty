package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午7:22
 */
public class FourIn extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        HttpRequest request = (HttpRequest) msg;
        request.setMethod(HttpMethod.GET);
        request.setUri("http://127.0.0.1/8191");
        request.setProtocolVersion(HttpVersion.HTTP_1_1);
        ctx.write(request);
    }
}
