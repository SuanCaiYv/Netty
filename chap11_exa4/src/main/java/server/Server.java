package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author SuanCaiYv
 * @time 2020/1/20 下午8:56
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
                .localAddress(new InetSocketAddress(8189))
                .channel(EpollServerSocketChannel.class)
                .childHandler(new OneChannelInitializer());
        ChannelFuture future = bootstrap.bind();
        future.addListener((ChannelFutureListener) future1 ->{
            if (future1.isSuccess()) {
                System.out.println("Binded");
            }
            else {
                System.out.println("Error");
            }
        });
        future.channel().closeFuture().sync();
        group1.shutdownGracefully().sync();
        group2.shutdownGracefully().sync();
    }
}
