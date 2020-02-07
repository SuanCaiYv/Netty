package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
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
        System.out.println(msg.getClass().getName());
        if (msg instanceof HttpObject) {
            HttpObject httpObject = (HttpObject) msg;
            FullHttpRequest request = (FullHttpRequest) httpObject;
            String uri = request.uri();
            if ("/ws".equalsIgnoreCase(uri)) {
                // 准备生成WebSocket握手器工厂
                WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory("ws://127.0.0.1:8189/ws", null, false);
                // 利用WebSocket工厂和HttpRequest生成握手器
                WebSocketServerHandshaker handshaker = handshakerFactory.newHandshaker(request);
                // 握手器进行协议升级, 然后告诉浏览器; 实际上就是移除Http处理器, 添加WebSocket处理器并写出一个FullHttpResponse, 里面包含了升级成功的信息
                handshaker.handshake(ctx.channel(), request);
            }
            else {
                ctx.fireChannelRead(request);
            }
        }
        else if (msg instanceof WebSocketFrame) {
            ctx.fireChannelRead(msg);
        }
        else {
            System.out.println("error");
        }
    }
}
