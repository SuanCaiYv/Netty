package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * @author joker
 * @time 2019/12/16 下午4:32
 */
public class FirstOutHandler extends ChannelOutboundHandlerAdapter
{
    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception
    {
        super.bind(ctx, localAddress, promise);
        System.out.println("Channel Bind To A Address");
        ctx.bind(localAddress, promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
    {
        super.close(ctx, promise);
        System.out.println("Channel Close");
        ctx.close(promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception
    {
        super.connect(ctx, remoteAddress, localAddress, promise);
        System.out.println("Channel Connect");
        ctx.connect(remoteAddress, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
    {
        super.disconnect(ctx, promise);
        System.out.println("Channel Disconnect");
        ctx.disconnect(promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception
    {
        super.read(ctx);
        System.out.println("Channel Read");
        ctx.read();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        super.write(ctx, msg, promise);
        System.out.println("Channel Write");
        ctx.write(msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        super.flush(ctx);
        System.out.println("Channel Flush");
        ctx.flush();
    }
}
