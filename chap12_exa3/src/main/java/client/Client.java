package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * @author SuanCaiYv
 * @time 2020/2/8 上午12:16
 */
public class Client
{
    public static void main(String[] args) throws URISyntaxException, InterruptedException
    {
        new Client().run();
    }
    public void run() throws URISyntaxException, InterruptedException
    {
        EventLoopGroup group = new EpollEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(EpollSocketChannel.class)
                .handler(new ChannelInitializerOne());
        ChannelFuture channelFuture = connect(bootstrap);
        Channel channel = channelFuture.channel();
        int cnt = 5;
        Scanner scanner = new Scanner(System.in);
        while (cnt > 0) {
            --cnt;
            channel.writeAndFlush(new TextWebSocketFrame(scanner.nextLine()));
        }
    }

    public ChannelFuture connect(Bootstrap bootstrap) throws URISyntaxException, InterruptedException
    {
        URI uri = new URI("ws://127.0.0.1:8189/ws_uri");
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, true, httpHeaders);
        ChannelFuture future = bootstrap.connect(uri.getHost(), uri.getPort()).sync();
        Channel channel = future.channel();
        // handshake()方法会向管道中写入一个FullHttpRequest, 所以需要管道流作为参数
        ChannelFuture channelFuture = handshaker.handshake(channel).sync();
        /*
        // 升级成功, 进行移除
        if (channelFuture.isSuccess()) {
            ChannelPipeline pipeline = future.channel().pipeline();
            ChannelHandler channelHandler = pipeline.get(HttpRequestEncoder.class);
            if (channelHandler != null) {
                pipeline.remove(HttpRequestEncoder.class);
            }
            channelHandler = pipeline.get(HttpResponseDecoder.class);
            if (channelHandler != null) {
                pipeline.remove(HttpResponseDecoder.class);
            }
            channelHandler = pipeline.get(HttpClientCodec.class);
            if (channelHandler != null) {
                pipeline.remove(HttpClientCodec.class);
            }
            channelHandler = pipeline.get(HttpObjectAggregator.class);
            if (channelHandler != null) {
                pipeline.remove(HttpObjectAggregator.class);
            }
            channelHandler = pipeline.get(HttpContentCompressor.class);
            if (channelHandler != null) {
                pipeline.remove(HttpContentCompressor.class);
            }
        }
        */
        return channelFuture;
    }
}
