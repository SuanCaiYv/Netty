package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author SuanCaiYv
 * @time 2020/2/7 下午6:44
 */
public class Client
{
    public static void main(String[] args)
    {
        new Client().run();
    }
    public void run()
    {
        EventLoopGroup loopGroup = new EpollEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .remoteAddress(new InetSocketAddress("127.0.0.1", 8189))
                .channel(EpollSocketChannel.class)
                .handler(new ChannelInitializerOne());
        ChannelFuture future = bootstrap.connect();
    }
}
