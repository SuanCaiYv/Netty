package client;

import client.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午6:48
 */
public class ThreeChannelInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("codec", new HttpClientCodec());
        pipeline.addLast(new HttpObjectAggregator(1024*1024));
        // 客户端添加解压缩来处理来自服务端的压缩内容
        // pipeline.addLast(new HttpContentDecompressor());
        pipeline.addLast(new LastOut());
        pipeline.addLast(new OneOut());
        pipeline.addLast(new ByteToStringCodec());
        pipeline.addLast(new OneIn());
        pipeline.addLast(new LastIn());
    }
}
