package client;

import handler.FirstClientInHandler;
import handler.SecondClientInHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;

/**
 * @author joker
 * @time 2019/12/16 下午6:46
 */
public class Client
{
    private static final int PORT = 8189;
    private static final String HOST_NAME = "localhost";
    public static void main(String[] args) throws InterruptedException
    {
        Client client = new Client();
        client.run();
    }
    public void run() throws InterruptedException
    {
        EventLoopGroup group = new EpollEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        FirstClientInHandler firstHandler = new FirstClientInHandler();
        SecondClientInHandler secondHandler = new SecondClientInHandler();
        bootstrap.group(group)
                .remoteAddress(new InetSocketAddress(HOST_NAME, PORT))
                .channel(EpollSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>()
                {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception
                    {
                        ch.pipeline().addLast(firstHandler);
                        ch.pipeline().addLast(secondHandler);
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect().sync();
        channelFuture.channel().closeFuture().sync();
    }
}
