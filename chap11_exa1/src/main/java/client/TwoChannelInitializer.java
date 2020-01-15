package client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午5:54
 */
public class TwoChannelInitializer extends ChannelInitializer<SocketChannel>
{
    /**
     * 客户端为"请求"编码, "回应"解码, 服务端相反
     * @param ch NA
     * @throws Exception NA
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("requestEncoder", new HttpRequestEncoder());
        pipeline.addLast("responseDecoder", new HttpResponseDecoder());
    }
}
