package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.*;

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
            ;
        }
        else if (msg instanceof BinaryWebSocketFrame) {
            ;
        }
        else if (msg instanceof ContinuationWebSocketFrame) {
            ;
        }
        // 默认类型由Netty处理, 如果不是WebSocket, 那么直接交给下一个
        else {
            ctx.fireChannelRead(msg);
        }
    }
    public void dealTextFrame(ChannelHandlerContext ctx, TextWebSocketFrame msg)
    {
        ;
    }
}
