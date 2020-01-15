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
 * @time 2020/1/15 下午6:59
 */
public class ThreeServer
{
    public static void main(String[] args) throws InterruptedException
    {
        ThreeServer server = new ThreeServer();
        server.run();
    }
    public void run() throws InterruptedException
    {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup group1 = new EpollEventLoopGroup();
        EventLoopGroup group2 = new EpollEventLoopGroup();
        bootstrap.group(group1, group2)
                .localAddress(new InetSocketAddress(8191))
                .channel(EpollServerSocketChannel.class)
                .childHandler(new ThreeChannelInitializer());
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
