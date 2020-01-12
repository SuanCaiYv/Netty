package server;

import handler.server.LastInboundHandler;
import handler.server.LastOutboundHandler;
import handler.server.OneInboundHandler;
import handler.server.OneOutboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;

/**
 * @author joker
 * @time 2020/1/12 下午4:32
 */
public class Server
{
    public static void main(String[] args) throws InterruptedException
    {
        Server server = new Server();
        server.run();
    }
    public void run() throws InterruptedException
    {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup group1 = new EpollEventLoopGroup();
        EventLoopGroup group2 = new EpollEventLoopGroup();
        bootstrap.group(group1, group2)
                .channel(EpollServerSocketChannel.class)
                .localAddress(new InetSocketAddress(8189))
                .childHandler(new ChannelInitializer<SocketChannel>()
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
        ChannelFuture future = bootstrap.bind().sync();
        future.channel().closeFuture().sync();
        group1.shutdownGracefully().sync();
        group2.shutdownGracefully().sync();
    }
}
