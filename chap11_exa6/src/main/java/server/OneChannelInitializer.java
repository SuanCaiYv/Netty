package server;

import handler.server.LastIn;
import handler.server.LastOut;
import handler.server.OneIn;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author SuanCaiYv
 * @time 2020/1/20 下午9:32
 */
public class OneChannelInitializer extends ChannelInitializer<SocketChannel>
{
    /**
     * ChunkedStream, 构造参数为InputStream, 从输入流逐块地读取内容, 返回值为ByteBuf, 避免了爆内存
     * 还有一个方法, ChunkedFile, 构造参数为File, 返回值为ByteBuf, 会逐块的读取文件并返回成ByteBuf, 避免了爆内存
     * @param ch NA
     * @throws Exception NA
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LastOut());
        // 添加ChunkedWriteHandler以处理作为ChunkedInput输入的数据
        pipeline.addLast(new ChunkedWriteHandler());
        // OneIn会把文件整成ChunkedInput然后写入
        pipeline.addLast(new OneIn());
        pipeline.addLast(new LastIn());
    }
}
