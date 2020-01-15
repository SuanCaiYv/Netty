package server;

import handler.server.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午7:52
 */
public class OneServerChannelInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LastOut());
        pipeline.addLast(new OneOut());
        pipeline.addLast("codec", new HttpServerCodec());
        // 10MB为最大消息大小
        pipeline.addLast(new HttpObjectAggregator(1024*1024*10));
        // 服务器端添加压缩来实现对于内容的压缩, netty支持deflate和gzip
        // pipeline.addLast(new HttpContentCompressor());
        pipeline.addLast(new OneIn());
        pipeline.addLast(new LastIn());
    }
}
