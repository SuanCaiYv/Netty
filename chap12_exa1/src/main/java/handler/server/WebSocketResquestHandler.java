package handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        if (msg instanceof TextWebSocketFrame) {
            dealTextFrame(ctx, (TextWebSocketFrame) msg);
        }
        else if (msg instanceof BinaryWebSocketFrame) {
            dealBinaryFrame(ctx, (BinaryWebSocketFrame) msg);
        }
        else if (msg instanceof ContinuationWebSocketFrame) {
            dealContinuation(ctx, (ContinuationWebSocketFrame) msg);
        }
        // 默认类型由Netty处理, 如果不是WebSocket, 那么直接交给下一个
        else {
            ctx.fireChannelRead(msg);
        }
    }

    public void dealTextFrame(ChannelHandlerContext ctx, TextWebSocketFrame textFrame)
    {
        String msg = "Get From "+ctx.channel().id()+": "+textFrame.text();
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(msg);
        // 或这样构造: textWebSocketFrame = new TextWebSocketFrame(Unpooled.copiedBuffer(msg.getBytes()));
        // 单独发送这样做: ctx.channel().write(textWebSocketFrame.retain());
        // 数据写回也必须是WebSocketFrame类型
        // 使用ctx.write(textWebSocketFrame);也可以, 只是只会写回到发送端, 而不是所有的客户端
        channelGroup.writeAndFlush(textWebSocketFrame);
    }

    public void dealBinaryFrame(ChannelHandlerContext ctx, BinaryWebSocketFrame binaryFrame)
    {
        ByteBuf byteBuf = binaryFrame.content();
    }

    public void dealContinuation(ChannelHandlerContext ctx, ContinuationWebSocketFrame continuationFrame)
    {
        ByteBuf byteBuf = continuationFrame.content();
    }
}
