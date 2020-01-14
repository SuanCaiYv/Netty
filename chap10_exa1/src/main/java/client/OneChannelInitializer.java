package client;

import handler.client.LastIn;
import handler.client.LastOut;
import handler.client.OneIn;
import handler.client.OneOut;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author joker
 * @date 2020/1/14 下午4:25
 */
public class OneChannelInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ch.pipeline().addLast(new LastOut());
        ch.pipeline().addLast(new OneOut());
        ch.pipeline().addLast(new OneIn());
        ch.pipeline().addLast(new LastIn());
    }
}
