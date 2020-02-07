package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author SuanCaiYv
 * @time 2020/2/8 上午12:16
 */
public class Client
{
    public static void main(String[] args)
    {
        new Client().run();
    }
    public void run()
    {
        EventLoopGroup group = new EpollEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(EpollSocketChannel.class)
                .remoteAddress(new InetSocketAddress("127.0.0.1", 8189))
                .handler(new ChannelInitializerOne());
        bootstrap.connect();
    }
}
