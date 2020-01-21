package client;

import handler.client.LastIn;
import handler.client.LastOut;
import handler.client.OneIn;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author SuanCaiYv
 * @time 2020/1/20 下午9:31
 */
public class OneChannelInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LastOut());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new OneIn());
        pipeline.addLast(new LastIn());
    }
}
