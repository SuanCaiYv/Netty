package handler.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * @author joker
 * @time 2019/12/16 下午9:38
 */
public class ClientFirstOutHandler extends ChannelOutboundHandlerAdapter
{
    private static final String ID = "ClientFirstOutHandler: ";
    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception
    {
        System.out.println(ID+"绑定到本地端口");
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception
    {
        System.out.println(ID+"连接到远程服务器");
        ctx.connect(remoteAddress, localAddress, promise);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        String m = "I'm A Text";
        ctx.write(Unpooled.copiedBuffer(m.getBytes()));
        ctx.flush();
        promise.addListener((ChannelFutureListener) future -> System.out.println("Done"));
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        ctx.flush();
    }
}
