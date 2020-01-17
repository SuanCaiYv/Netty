package server;

import handler.server.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午10:48
 */
public class OneChannelInitializer extends ChannelInitializer<SocketChannel>
{
    /**
     * 如果3秒没有读到东西, 会触发入站的userEventTriggered()方法
     * @param ch NA
     * @throws Exception NA
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LastOut());
        pipeline.addLast(new ByteToStringCodec());
        pipeline.addLast(new IdleStateHandler(3, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast(new OneIn());
        pipeline.addLast(new HeartbeatHandler());
        pipeline.addLast(new LastIn());
    }
}
