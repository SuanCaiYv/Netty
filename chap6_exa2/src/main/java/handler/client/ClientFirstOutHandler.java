package handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

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
        super.bind(ctx, localAddress, promise);
        System.out.println(ID+"绑定到本地端口");
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception
    {
        super.connect(ctx, remoteAddress, localAddress, promise);
        System.out.println(ID+"连接到远程服务器");
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        System.out.println(ID+"写出: "+((ByteBuf) msg).toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush(msg, promise);
        promise.addListener((ChannelFutureListener) future -> System.out.println("Done"));
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        super.flush(ctx);
        ctx.flush();
    }
}
