package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetSocketAddress;

/**
 * WebSocket升级需要先向服务器申请升级(发送申请数据包, 既一个请求), 服务器返回升级完成数据包, 然后客户端再升级
 * @author SuanCaiYv
 * @time 2020/2/8 上午12:16
 */
public class Server
{
    public static void main(String[] args)
    {
        new Server().run();
    }

    /**
     * 来自网络: 客户端向服务器发送一个升级协议的HTTP请求, 这个请求头部包含Connection和Upgrade字段, 表示客户端需要使用WebSocket协议.
     * 服务器将HTTP协议升级成WebSocket协议后返回客户端响应数据, 即完成了握手阶段, 建立了WebSocket连接, 这个连接会持续存在, 直到客户端或服务器主动关闭连接.
     */
    public void run()
    {
        EventLoopGroup bossGroup = new EpollEventLoopGroup();
        EventLoopGroup workGroup = new EpollEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        serverBootstrap.group(bossGroup, workGroup)
                .localAddress(new InetSocketAddress(8189))
                .channel(EpollServerSocketChannel.class)
                .childHandler(new ChannelInitializerOne(channels));
        serverBootstrap.bind();
    }
}
