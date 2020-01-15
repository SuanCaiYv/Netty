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
    /**
     * 在Http处理中, 添加上SslHandler即可实现HTTPS作用
     * @param ch NA
     * @throws Exception NA
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        // 获得加密引擎
        SSLEngine sslEngine = SSLContextGenerator.getServerContext().createSSLEngine();
        // 设置为服务端模式
        sslEngine.setUseClientMode(false);
        // 设置为需要权限认证
        sslEngine.setNeedClientAuth(true);
        // 把SslHandler添加在最前面,即可实现对于所有入站数据的拦截(解密)和出站数据的加密
        pipeline.addLast("ssl", new SslHandler(sslEngine));
        pipeline.addLast(new LastOut());
        pipeline.addLast(new OneOut());
        pipeline.addLast(new ByteToStringCodec());
        pipeline.addLast(new OneIn());
        pipeline.addLast(new LastIn());
    }
}
