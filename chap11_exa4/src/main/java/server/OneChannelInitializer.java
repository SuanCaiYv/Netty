package server;

import handler.server.LastIn;
import handler.server.LastOut;
import handler.server.OneIn;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author SuanCaiYv
 * @time 2020/1/20 下午9:32
 */
public class OneChannelInitializer extends ChannelInitializer<SocketChannel>
{
    /**
     * 使用"零拷贝"的FileRegion实现文件的传输
     * @param ch NA
     * @throws Exception NA
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LastOut());
        pipeline.addLast(new OneIn());
        pipeline.addLast(new LastIn());
    }
}
