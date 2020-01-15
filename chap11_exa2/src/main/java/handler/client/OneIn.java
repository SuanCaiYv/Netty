package handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午7:55
 */
public class OneIn extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        String msg = "text";
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "http://127.0.0.1/8191");
        request.content().writeBytes(msg.getBytes());
        request.setMethod(HttpMethod.GET);
        request.setUri("http://127.0.0.1:8191");
        request.setProtocolVersion(HttpVersion.HTTP_1_1);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        request.headers().set(HttpHeaderNames.HOST, "localhost");
        ctx.write(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        HttpObject object = (HttpObject) msg;
        FullHttpResponse response = (FullHttpResponse) object;
        String txt = response.content().toString(CharsetUtil.UTF_8);
        System.out.println("读到: "+txt);
    }
}
