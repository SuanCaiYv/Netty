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
 * @time 2020/1/13 下午5:23
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
        // ServerBootstrap拥有两个channel, 一个是ServerSocketChannel,专门用来监听端口, 一个是SocketChannel,是监听到了之后返回的与客户端的连接
        // 所有带child的方法都是作用在子channel上的(返回的channel)
        // 当bind()方法被调用时,会返回一个ServerChannel, 当连接被接受时,会返回一个SocketChannel,既子Channel
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup group1 = new EpollEventLoopGroup();
        EventLoopGroup group2 = new EpollEventLoopGroup();
        bootstrap.group(group1, group2)
                // 只是表示将会绑定到这个端口,但是还没开始绑定,直到调用bind()方法为止
                .localAddress(new InetSocketAddress(8190))
                .channel(EpollServerSocketChannel.class)
                .childHandler(new ChannelInitializerImpl());
        ChannelFuture future = bootstrap.bind();
        future.addListener(new ChannelFutureListener()
        {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception
            {
                if (future.isSuccess()) {
                    System.out.println("Success");
                }
                else {
                    System.out.println("Error!");
                }
            }
        });
        // future返回的channel是服务端的ServerSocketChannel,是唯一的
        future.channel().closeFuture().sync();
        group1.shutdownGracefully().sync();
        group2.shutdownGracefully().sync();
    }
}
