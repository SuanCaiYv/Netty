package server;

import handler.server.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午7:52
 */
public class OneServerChannelInitializer extends ChannelInitializer<SocketChannel>
{
    /**
     * 执行过程: HttpServerCodec把字节流转成散装的Http, HttpObjectAggregator把散装的聚合成完整的
     * 出站: HttpServerCodec把Http(完整的或分散的)转换成字节流, 在OneOut完成发送
     * @param ch NA
     * @throws Exception NA
     */
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
        pipeline.addLast(new HttpContentCompressor());
        pipeline.addLast(new OneIn());
        pipeline.addLast(new LastIn());
    }
}
