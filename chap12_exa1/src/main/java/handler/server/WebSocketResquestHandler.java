package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author SuanCaiYv
 * @time 2020/2/6 上午12:27
 */
public class WebSocketResquestHandler extends ChannelInboundHandlerAdapter
{
    private final ChannelGroup channelGroup;

    public WebSocketResquestHandler(ChannelGroup channelGroup)
    {
        this.channelGroup = channelGroup;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        if (evt == WebSocketServerProtocolHandler.HandshakeComplete.class) {
            ctx.pipeline().remove(HttpRequestHandler.class);
            // 对管道流组写将会对于组内每一个成员实现写
            channelGroup.writeAndFlush("Client: "+ctx.channel()+" Joined");
            channelGroup.add(ctx.channel());
        }
        else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println("qwer");
        TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
        ctx.writeAndFlush(textWebSocketFrame);
    }
}
