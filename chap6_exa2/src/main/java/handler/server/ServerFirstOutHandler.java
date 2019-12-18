package handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;

/**
 * @author joker
 * @time 2019/12/18 下午3:22
 */
public class ServerFirstOutHandler extends ChannelOutboundHandlerAdapter
{
    private static final String ID = "ServerFirstOutHandler: ";
    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception
    {
        super.bind(ctx, localAddress, promise);
        System.out.println(ID+"服务器已绑定到端口");
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
    {
        super.close(ctx, promise);
        System.out.println(ID+"操作流已关闭");
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        System.out.println(ID+"写出: "+((ByteBuf) msg).toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush(msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        super.flush(ctx);
        ctx.flush();
    }
}
