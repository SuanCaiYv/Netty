package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

/**
 * @author SuanCaiYv
 * @time 2020/2/7 下午5:09
 */
public class OneIn extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println("qwer");
        // System.out.println(msg.getClass().getName());
        /*
        HttpObject httpObject = (HttpObject) msg;
        FullHttpRequest request = (FullHttpRequest) httpObject;
        String uri = request.uri();
        WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory("ws://127.0.0.1:8189/ws", null, false);
        */
    }
}
