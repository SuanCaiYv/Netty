package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author SuanCaiYv
 * @time 2020/2/8 上午12:23
 */
public class WebSocketRequestHandler extends ChannelInboundHandlerAdapter
{
    private ChannelGroup channelGroup;

    public WebSocketRequestHandler(ChannelGroup channelGroup)
    {
        this.channelGroup = channelGroup;
    }

    /**
     * 注意, 一旦有新的连接建立, WebSocketServerProtocolHandler就会触发事件并传播下去
     * 那么我们在此处理, 也就是把管道流添加到管道流组里去
     * @param ctx NA
     * @param evt NA
     * @throws Exception NA
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        // 每次连接会传过来两个事件: WebSocketServerProtocolHandler$ServerHandshakeStateEvent和WebSocketServerProtocolHandler$HandshakeComplete
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            channelGroup.add(ctx.channel());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        dispatcher(ctx, msg);
    }

    public void dispatcher(ChannelHandlerContext ctx, Object msg)
    {
        if (msg instanceof TextWebSocketFrame) {
            dealTextFrame(ctx, (TextWebSocketFrame) msg);
        }
        else if (msg instanceof BinaryWebSocketFrame) {
            dealBinaryFrame(ctx, (BinaryWebSocketFrame) msg);
        }
        else if (msg instanceof ContinuationWebSocketFrame) {
            dealContinuationFrame(ctx, (ContinuationWebSocketFrame) msg);
        }
        else {
            // do something...
        }
    }

    public void dealTextFrame(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame)
    {
        String msg = "从"+ctx.channel().id()+"读到了: "+textWebSocketFrame.text();
        System.out.println(msg);
        TextWebSocketFrame text = new TextWebSocketFrame(msg);
        channelGroup.writeAndFlush(text);
    }

    public void dealBinaryFrame(ChannelHandlerContext ctx, BinaryWebSocketFrame binaryWebSocketFrame)
    {
        ;
    }

    public void dealContinuationFrame(ChannelHandlerContext ctx, ContinuationWebSocketFrame continuationWebSocketFrame)
    {
        ;
    }

}
