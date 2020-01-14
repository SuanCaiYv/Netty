package client;

import handler.client.*;
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
        ch.pipeline().addLast(new StringToByteEncoder());
        ch.pipeline().addLast(new IntegerToStringEncoder());
        ch.pipeline().addLast(new TwoIn());
        ch.pipeline().addLast(new LastIn());
    }
}
