package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author SuanCaiYv
 * @time 2020/2/8 上午12:23
 */
public class WebSocketRequestHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        dispatcher(ctx, msg);
    }

    public void dispatcher(ChannelHandlerContext ctx, Object msg)
    {
        if (msg instanceof TextWebSocketFrame) {
            ;
        }
        else if (msg instanceof BinaryWebSocketFrame) {
            ;
        }
        else if (msg instanceof ContinuationWebSocketFrame) {
            ;
        }
        else {
            ;
        }
    }
}
