package server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午5:56
 */
public class TwoChannelInitializer extends ChannelInitializer<SocketChannel>
{
    /**
     * 服务端为"Request"解码, 编码"Response"
     * @param ch NA
     * @throws Exception NA
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("requestDecoder", new HttpRequestDecoder());
        pipeline.addLast("responseEncoder", new HttpResponseEncoder());
    }
}
