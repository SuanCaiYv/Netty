package handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

/**
 * @author SuanCaiYv
 * @time 2020/2/7 下午6:46
 */
public class OneIn extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/ws");
        byte[] bytes = "text".getBytes();
        request.content().writeBytes("text".getBytes());
        request.headers().set(HttpHeaderNames.HOST, "localhost:8189");
        request.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, bytes.length);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE);
        request.headers().set(HttpHeaderNames.ORIGIN, "http://127.0.0.1:8189/ws");
        request.headers().set(HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET);
        request.headers().set(HttpHeaderNames.SEC_WEBSOCKET_VERSION, 13);
        request.headers().set(HttpHeaderNames.SEC_WEBSOCKET_KEY, "Oy4NRAQ13jhfONC7bP8dTKb4PTU=");
        ctx.write(request);
    }
}
