package handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author SuanCaiYv
 * @time 2020/2/8 上午12:25
 */
public class WebSocketResponseHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println(msg.getClass().getName());
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
