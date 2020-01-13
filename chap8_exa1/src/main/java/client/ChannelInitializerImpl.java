package client;

import handler.client.OneInbound;
import handler.client.OneOutbound;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author SuanCaiYv
 * @time 2020/1/13 下午5:25
 */
public class ChannelInitializerImpl extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ch.pipeline().addLast(new OneOutbound());
        ch.pipeline().addLast(new OneInbound());
    }
}
