package client;

import handler.client.LastIn;
import handler.client.LastOut;
import handler.client.OneOut;
import handler.client.TwoIn;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author SuanCaiYv
 * @time 2020/1/14 下午5:40
 */
public class TwoChannelInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ch.pipeline().addLast(new LastOut());
        ch.pipeline().addLast(new OneOut());
        ch.pipeline().addLast(new TwoIn());
        ch.pipeline().addLast(new LastIn());
    }
}
