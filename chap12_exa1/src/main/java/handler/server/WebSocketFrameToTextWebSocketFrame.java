package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

/**
 * @author SuanCaiYv
 * @time 2020/2/6 下午1:54
 */
public class WebSocketFrameToTextWebSocketFrame extends MessageToMessageDecoder<WebSocketFrame>
{
    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception
    {
        out.add(msg);
    }
}
