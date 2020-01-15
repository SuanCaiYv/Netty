package client;

import client.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import tools.SSLContextGenerator;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午12:55
 */
public class OneChannelInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        SSLContext context = SSLContextGenerator.getClientContext();
        SSLEngine sslEngine = context.createSSLEngine();
        sslEngine.setUseClientMode(true);
        // 客户端第一个写入的消息不应该被加密, 所以要设置为true
        pipeline.addLast("ssl", new SslHandler(sslEngine));
        pipeline.addLast(new LastOut());
        pipeline.addLast(new OneOut());
        pipeline.addLast(new ByteToStringCodec());
        pipeline.addLast(new OneIn());
        pipeline.addLast(new LastIn());
    }
}
