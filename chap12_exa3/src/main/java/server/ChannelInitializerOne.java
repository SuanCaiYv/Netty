package server;

import handler.server.LastIn;
import handler.server.LastOut;
import handler.server.WebSocketRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * 准确来说, 不是WebSocketServerProtocolHandler移除了那些类, 而是它调用的handshaker()进行协议握手升级时移除的
 * @author SuanCaiYv
 * @time 2020/2/8 上午12:16
 */
public class ChannelInitializerOne extends ChannelInitializer<SocketChannel>
{
    private ChannelGroup channels;

    public ChannelInitializerOne(ChannelGroup channels)
    {
        this.channels = channels;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception
    {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LastOut());
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(1024*1024*10));
        // 这个类会移除HttpContentCompressor和HttpObjectAggregator(如果他们存在的话), 然后添加WebSocket编码器和解码器
        /*
        关于添加, 如果存在HttpRequestDecoder, 就用WebSocketDecoder取代他, 把WebSocketEncoder添加到HttpResponseEncoder前面去
        如果HttpServerCodec存在, 就把WebSocket编解码器(两个)添加到他的前面
        所以最后都会保证, 字节流<->WebSocket WebSocket<-Http
         */
        // 此类接受一个FullHttpRequest作为读取的数据, 然后把它升级为(前提这个请求申请了升级)WebSocket
        // 这个类还会处理Ping和Pong帧, 以减轻程序员负担
        // 入参为uri, 这个类作用类似创建握手类并调用握手方法并在握手完成后触发事件传递
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws_uri"));
        pipeline.addLast(new WebSocketRequestHandler(channels));
        pipeline.addLast(new LastIn());
    }
}
