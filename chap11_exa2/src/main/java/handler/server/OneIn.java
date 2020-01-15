package handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午7:54
 */
public class OneIn extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        HttpObject object = (HttpObject) msg;
        FullHttpRequest request = (FullHttpRequest) object;
        String txt = request.content().toString(CharsetUtil.UTF_8);
        System.out.println("读到: "+txt);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.ACCEPTED);
        String str = "another text";
        ByteBuf byteBuf = Unpooled.copiedBuffer(str.getBytes());
        response.content().writeBytes(byteBuf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ctx.write(response);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("活动状态");
    }
}
