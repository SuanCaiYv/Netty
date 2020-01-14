package server;

import handler.server.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 上午12:30
 */
public class ThreeChannelInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ch.pipeline().addLast(new LastOut());
        ch.pipeline().addLast(new OneOut());
        ch.pipeline().addLast(new StringToByteEncoder());
        ch.pipeline().addLast(new ByteToStringDecoder());
        ch.pipeline().addLast(new StringToIntegerCodec());
        ch.pipeline().addLast(new ThreeInbound());
        ch.pipeline().addLast(new LastIn());
    }
}
