package client;

import handler.client.LastInboundHandler;
import handler.client.LastOutboundHandler;
import handler.client.OneInboundHandler;
import handler.client.OneOutboundHandler;
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
 * @time 2020/1/12 下午4:32
 */
public class Client
{
    public static void main(String[] args) throws InterruptedException
    {
        Client client = new Client();
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
                        ch.pipeline().addLast(new OneOutboundHandler());
                        ch.pipeline().addLast(new OneInboundHandler());
                        ch.pipeline().addLast(new LastInboundHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect().sync();
        future.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}
