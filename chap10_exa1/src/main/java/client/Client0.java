package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author SuanCaiYv
 * @time 2020/1/14 下午5:41
 */
public class Client0
{
    public static void main(String[] args) throws InterruptedException
    {
        Client0 client0 = new Client0();
        client0.run();
    }
    public void run() throws InterruptedException
    {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new EpollEventLoopGroup();
        bootstrap.group(group)
                .remoteAddress(new InetSocketAddress("localhost", 8190))
                .channel(EpollSocketChannel.class)
                .handler(new TwoChannelInitializer());
        ChannelFuture future = bootstrap.connect();
        future.addListener((ChannelFutureListener) future1 -> {
            if (future1.isSuccess()) {
                System.out.println("Connected");
            }
            else {
                System.out.println("Error");
            }
        });
        future.channel().closeFuture().sync();
        group.shutdownGracefully().sync();
    }
}
