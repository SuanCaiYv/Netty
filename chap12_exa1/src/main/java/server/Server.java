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
 * WebSocket协议实质上是Http的升级, 所谓的升级就是发送一个升级包(本质是一个Request, 包含了客户端信息)
 * 然后服务端移除Http处理器, 添加WebSocket处理器, 然后告诉浏览器升级成功, 然后就可以使用WebSocket进行通信
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
        // 将会保存所有已经连接的WebSocket Channel
        ChannelGroup channels = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
        EventLoopGroup bossGroup = new EpollEventLoopGroup();
        EventLoopGroup workGroup = new EpollEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .localAddress(8189)
                .childHandler(new ChannelInitializerOne(channels))
                .channel(EpollServerSocketChannel.class);
        ChannelFuture channelFuture = serverBootstrap.bind().sync();
    }
}
