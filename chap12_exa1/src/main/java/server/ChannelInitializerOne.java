package server;

import handler.server.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

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
        pipeline.addLast(new OneIn(channelGroup));
        /*
        // 让Netty处理不常用的帧, 接受的信息为FullHttpRequest, 并进行协议升级, 但是我不会用, 于是自己造了轮子
        pipeline.addLast(new WebSocketServerProtocolHandler("ws://127.0.0.1:8189/ws"));
        */
        pipeline.addLast(new HttpRequestHandler());
        pipeline.addLast(new WebSocketResquestHandler(channelGroup));
        pipeline.addLast(new LastIn());
    }
}
