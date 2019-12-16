package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

import java.net.SocketAddress;

/**
 * @author joker
 * @time 2019/12/16 下午4:32
 */
public class SecondOutHandler extends ChannelOutboundHandlerAdapter
{
    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception
    {
        super.bind(ctx, localAddress, promise);
        System.out.println("Channel Bind To A Address");
        promise.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("Channel Bind Successfully");
            }
            else {
                Throwable throwable = future.cause();
                System.out.println(throwable.getMessage());
            }
        });
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
    {
        super.close(ctx, promise);
        System.out.println("Channel Close");
        promise.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("Channel Close Successfully");
            }
            else {
                Throwable throwable = future.cause();
                System.out.println(throwable.getMessage());
            }
        });
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception
    {
        super.connect(ctx, remoteAddress, localAddress, promise);
        System.out.println("Channel Connect");
        promise.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("Channel Connect Successfully!");
            }
            else {
                Throwable throwable = future.cause();
                System.out.println(throwable.getMessage());
            }
        });
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
    {
        super.disconnect(ctx, promise);
        System.out.println("Channel Disconnect");
        promise.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("Channel DisConnect Successfully");
            }
            else {
                Throwable throwable = future.cause();
                System.out.println(throwable.getMessage());
            }
        });
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
        ByteBuf in = (ByteBuf) msg;
        ctx.write(in);
        promise.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("Channel Write Successfully");
            }
            else {
                Throwable throwable = future.cause();
                System.out.println(throwable.getMessage());
            }
        });
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        super.flush(ctx);
        System.out.println("Channel Flush");
        ctx.flush();
    }
}
