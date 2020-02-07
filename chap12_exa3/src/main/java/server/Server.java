package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;

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
    public void run()
    {
        EventLoopGroup bossGroup = new EpollEventLoopGroup();
        EventLoopGroup workGroup = new EpollEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .localAddress(new InetSocketAddress(8189))
                .channel(EpollServerSocketChannel.class)
                .childHandler(new ChannelInitializerOne());
        serverBootstrap.bind();
    }
}
