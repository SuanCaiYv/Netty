package client;

import handler.client.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;

/**
 * @author joker
 * @date 2020/1/13 上午11:02
 */
public class Client0
{
    public static void main(String[] args) throws InterruptedException
    {
        Client0 client = new Client0();
        client.run();
    }
    public void run() throws InterruptedException
    {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new EpollEventLoopGroup();
        bootstrap.group(group)
                .channel(EpollSocketChannel.class)
                .remoteAddress(new InetSocketAddress("localhost", 8189))
                .handler(new ChannelInitializer<SocketChannel>()
                {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception
                    {
                        ch.pipeline().addLast(new LastOutboundHandler());
                        ch.pipeline().addLast(new TwoOutboundHandler());
                        ch.pipeline().addLast(new OneOutboundHandler());
                        ch.pipeline().addLast(new InboundHandler());
                        ch.pipeline().addLast(new LastInboundHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect().sync();
        future.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}
