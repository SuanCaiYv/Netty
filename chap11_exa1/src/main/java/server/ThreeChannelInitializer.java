package server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import server.handler.*;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午6:49
 */
public class ThreeChannelInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("codec", new HttpServerCodec());
        // 1MB为最大消息大小
        pipeline.addLast(new HttpObjectAggregator(1024*1024));
        // 服务器端添加压缩来实现对于内容的压缩, netty支持deflate和gzip
        // pipeline.addLast(new HttpContentCompressor());
        pipeline.addLast(new LastOut());
        pipeline.addLast(new OneOut());
        pipeline.addLast(new ByteToStringCodec());
        pipeline.addLast(new OneIn());
        pipeline.addLast(new LastIn());
    }
}
