package client;

import handler.client.LastIn;
import handler.client.LastOut;
import handler.client.OneIn;
import handler.client.OneOut;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午7:50
 */
public class OneClientChannelInitializer extends ChannelInitializer<SocketChannel>
{
    /**
     * HttpClientCodec整合了解码和编码Http
     * HttpObjectAggregator可以返回一个完整(Full)的Http响应, 但是, 要注意类型转换的坑!!!
     * @param ch NA
     * @throws Exception NA
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LastOut());
        pipeline.addLast(new OneOut());
        pipeline.addLast("codec", new HttpClientCodec());
        // 设置Http最大为10M
        pipeline.addLast("aggregator", new HttpObjectAggregator(1024*1024*10));
        // 客户端添加解压缩来处理来自服务端的压缩内容
        pipeline.addLast(new HttpContentDecompressor());
        pipeline.addLast(new OneIn());
        pipeline.addLast(new LastIn());
    }
}
