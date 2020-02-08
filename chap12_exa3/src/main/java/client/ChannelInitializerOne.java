package client;

import handler.client.LastIn;
import handler.client.LastOut;
import handler.client.WebSocketResponseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;

/**
 * @author SuanCaiYv
 * @time 2020/2/8 上午12:17
 */
public class ChannelInitializerOne extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception
    {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LastOut());
        pipeline.addLast(new HttpClientCodec());
        // 把WebSocketEncoder加入到HttpRequestEncoder后面去
        // 字节流->Http Http<-WebSocket???不对, 在升级成功后, 应该手动移除Http编解码器
        pipeline.addLast(new HttpObjectAggregator(1024*1024*10));
        pipeline.addLast(new WebSocketClientProtocolHandler(WebSocketClientProtocolConfig.newBuilder().build()));
        pipeline.addLast(new WebSocketResponseHandler());
        pipeline.addLast(new LastIn());
    }
}
