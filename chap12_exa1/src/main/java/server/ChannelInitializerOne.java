package server;

import handler.server.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author SuanCaiYv
 * @time 2020/2/5 下午11:27
 */
public class ChannelInitializerOne extends ChannelInitializer<SocketChannel>
{
    private ChannelGroup channelGroup;

    public ChannelInitializerOne(ChannelGroup channelGroup)
    {
        this.channelGroup = channelGroup;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LastOut());
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(1024*1024*10));
        pipeline.addLast(new OneIn());
        // 处理协议升级握手
        // pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new HttpRequestHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler("ws://127.0.0.1:8189/ws"));
        pipeline.addLast(new WebSocketResquestHandler(channelGroup));
        pipeline.addLast(new LastIn());
    }
}
