package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;

/**
 * @author SuanCaiYv
 * @time 2020/2/5 下午11:27
 */
public class Server
{
    public static void main(String[] args) throws InterruptedException
    {
        new Server().run();
    }
    public void run() throws InterruptedException
    {
        EventLoopGroup bossGroup = new EpollEventLoopGroup();
        EventLoopGroup workGroup = new EpollEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .localAddress(8189)
                .childHandler(new ChannelInitializerOne())
                .channel(EpollServerSocketChannel.class);
        ChannelFuture channelFuture = serverBootstrap.bind().sync();
    }
}
