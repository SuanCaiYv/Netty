package server;

import handler.server.LastInbound;
import handler.server.OneOutbound;
import handler.server.TwoInbound;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author joker
 * @date 2020/1/13 下午8:36
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
