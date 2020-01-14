package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author joker
 * @date 2020/1/13 下午8:12
 */
public class Server0
{
    public static void main(String[] args) throws InterruptedException
    {
        Server0 server0 = new Server0();
        server0.run();
    }
    public void run() throws InterruptedException
    {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup group1 = new EpollEventLoopGroup();
        EventLoopGroup group2 = new EpollEventLoopGroup();
        bootstrap.group(group1, group2)
                .localAddress(new InetSocketAddress(8189))
                .channel(EpollServerSocketChannel.class)
                .childHandler(new ChannelInitializerImpl0());
        ChannelFuture future = bootstrap.bind();
        future.addListener(new ChannelFutureListener()
        {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception
            {
                if (channelFuture.isSuccess()) {
                    System.out.println("Success");
                }
                else {
                    System.out.println("Error!");
                }
            }
        });
        future.channel().closeFuture().sync();
        group1.shutdownGracefully();
        group2.shutdownGracefully();
    }
}
