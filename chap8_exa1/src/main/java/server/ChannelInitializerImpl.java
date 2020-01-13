package server;

import handler.server.OneInbound;
import handler.server.OneOutbound;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ServerChannel;

/**
 * @author SuanCaiYv
 * @time 2020/1/13 下午5:24
 */
public class ChannelInitializerImpl extends ChannelInitializer<ServerChannel>
{
    @Override
    protected void initChannel(ServerChannel ch) throws Exception
    {
        ch.pipeline().addLast(new OneOutbound());
        ch.pipeline().addLast(new OneInbound());
    }
}
