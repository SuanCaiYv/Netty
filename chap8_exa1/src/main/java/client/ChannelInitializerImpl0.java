package client;

import handler.client.LastInbound;
import handler.client.OneOutbound;
import handler.client.TwoInbound;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author joker
 * @date 2020/1/13 下午8:11
 */
public class ChannelInitializerImpl0 extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception
    {
        socketChannel.pipeline().addLast(new OneOutbound());
        socketChannel.pipeline().addLast(new TwoInbound());
        socketChannel.pipeline().addLast(new LastInbound());
    }
}
