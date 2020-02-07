package client;

import handler.client.LastIn;
import handler.client.LastOut;
import handler.client.OneIn;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * @author SuanCaiYv
 * @time 2020/2/7 下午6:44
 */
public class ChannelInitializerOne extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LastOut());
        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast(new HttpObjectAggregator(1024*1024*10));
        pipeline.addLast(new OneIn());
        pipeline.addLast(new LastIn());
    }
}
