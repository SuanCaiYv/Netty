package server;

import handler.server.ServerFirstInHandler;
import handler.server.ServerFirstOutHandler;
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
 * @time 2019/12/16 下午9:01
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
                        ch.pipeline().addLast(new ServerFirstOutHandler());
                        ch.pipeline().addLast(new ServerFirstInHandler());
                        /*
                        在InboundHandler执行完成需要调用OutboundHandler的时候,
                        比如在InboundHandler调用ctx.writeAndFlush()方法,
                        Netty是直接从该InboundHandler返回逆序的查找该InboundHandler之前的OutboundHandler,
                        并非从Pipeline的最后一项Handler开始查找。所以OutboundHandler一定要在最后一个InboundHandler之前
                         */
                    }
                });
        ChannelFuture channelFuture = bootstrap.bind().sync();
        channelFuture.channel().closeFuture().sync();
        group1.shutdownGracefully().sync();
        group2.shutdownGracefully().sync();
    }
}
/*

1
ServerFirstInHandler: 连接被注册
ServerFirstInHandler: 连接处于活动状态
ServerFirstInHandler: 开始读...
ServerFirstInHandler: 读到了客户端说的: 我是客户端文本
ServerFirstInHandler: 读取完成, 准备写入
写入成功

ClientFirstInHandler: 连接被注册
ClientFirstOutHandler: 连接到远程服务器
ClientFirstInHandler: 连接处于活动状态
ClientFirstInHandler: 开始读...
ClientFirstInHandler: 读到了服务器说的: 我是服务端文本服务端写入完成
ClientFirstInHandler: 读取完成

2
ServerFirstInHandler: 连接被注册
ServerFirstInHandler: 连接处于活动状态
ServerFirstInHandler: 开始读...
ServerFirstInHandler: 读到了客户端说的: 我是客户端文本
ServerFirstOutHandler: 写出: 我是服务端文本
ServerFirstInHandler: 读取完成, 准备写入
ServerFirstOutHandler: 写出:
写入成功
ServerFirstOutHandler: 写出: 服务端写入完成

ClientFirstInHandler: 连接被注册
ClientFirstOutHandler: 连接到远程服务器
ClientFirstInHandler: 连接处于活动状态
ClientFirstInHandler: 开始读...
ClientFirstInHandler: 读到了服务器说的: 我是服务端文本
ClientFirstInHandler: 读取完成
ClientFirstInHandler: 开始读...
ClientFirstInHandler: 读到了服务器说的: 服务端写入完成
ClientFirstInHandler: 读取完成

3
ServerFirstInHandler: 连接被注册
ServerFirstInHandler: 连接处于活动状态
ServerFirstInHandler: 开始读...
ServerFirstInHandler: 读到了客户端说的: 我是客户端文本
ServerFirstInHandler: 读取完成, 准备写入
写入成功

ClientFirstInHandler: 连接被注册
ClientFirstOutHandler: 连接到远程服务器
ClientFirstOutHandler: 写出: 我是客户端文本
Done
ClientFirstInHandler: 连接处于活动状态
ClientFirstInHandler: 开始读...
ClientFirstInHandler: 读到了服务器说的: 我是服务端文本服务端写入完成
ClientFirstInHandler: 读取完成

4
ServerFirstInHandler: 连接被注册
ServerFirstInHandler: 连接处于活动状态
ServerFirstInHandler: 开始读...
ServerFirstInHandler: 读到了客户端说的: 我是客户端文本
ServerFirstOutHandler: 写出: 我是服务端文本
ServerFirstInHandler: 读取完成, 准备写入
ServerFirstOutHandler: 写出:
写入成功
ServerFirstOutHandler: 写出: 服务端写入完成

ClientFirstInHandler: 连接被注册
ClientFirstOutHandler: 连接到远程服务器
ClientFirstOutHandler: 写出: 我是客户端文本
Done
ClientFirstInHandler: 连接处于活动状态
ClientFirstInHandler: 开始读...
ClientFirstInHandler: 读到了服务器说的: 我是服务端文本
ClientFirstInHandler: 读取完成
ClientFirstInHandler: 开始读...
ClientFirstInHandler: 读到了服务器说的: 服务端写入完成
ClientFirstInHandler: 读取完成
 */
