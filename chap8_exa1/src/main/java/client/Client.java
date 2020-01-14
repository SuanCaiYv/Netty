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
 * @time 2020/1/13 下午5:22
 */
public class Client
{
    public static void main(String[] args) throws InterruptedException
    {
        Client client = new Client();
        client.run();
    }
    public void run() throws InterruptedException
    {
        EventLoopGroup group = new EpollEventLoopGroup();
        // Bootstrap可以用于客户端和无协议的连接
        // bind()和connect()方法都会返回一个已连接的channel,前者是UDP的连接,后者是TCP的连接
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .remoteAddress(new InetSocketAddress("localhost", 8189))
                .channel(EpollSocketChannel.class)
                .handler(new ChannelInitializerImpl());
        ChannelFuture future = bootstrap.connect();
        future.addListener(new ChannelFutureListener()
        {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception
            {
                if (future.isSuccess()) {
                    System.out.println("Connected");
                }
                else {
                    System.out.println("Error!");
                }
            }
        });
        future.channel().closeFuture().sync();
        group.shutdownGracefully().sync();
    }
}
