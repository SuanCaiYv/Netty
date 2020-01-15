package server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import server.handler.*;
import tools.SSLContextGenerator;

import javax.net.ssl.SSLEngine;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午1:45
 */
public class OneChannelInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine sslEngine = SSLContextGenerator.getServerContext().createSSLEngine();
        sslEngine.setUseClientMode(false);
        sslEngine.setNeedClientAuth(true);
        pipeline.addLast("ssl", new SslHandler(sslEngine));
        pipeline.addLast(new LastOut());
        pipeline.addLast(new OneOut());
        pipeline.addLast(new ByteToStringCodec());
        pipeline.addLast(new OneIn());
        pipeline.addLast(new LastIn());
    }
}
