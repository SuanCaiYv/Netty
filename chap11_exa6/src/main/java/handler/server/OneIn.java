package handler.server;

import io.netty.channel.*;
import io.netty.handler.stream.ChunkedStream;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author SuanCaiYv
 * @time 2020/1/20 下午9:38
 */
public class
OneIn extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        File file = new File("/home/joker/IdeaProjects/Netty/chap11_exa4/src/main/java/static/IMG_0286.JPG");
        FileInputStream fileInputStream = new FileInputStream(file);
        // 开启ChunkedStream写入文件, new ChunkedStream(<文件输入流>)会自动把文件读成ChunkedInput类型
        // 这是一个可以逐块地读取的类型, 所以不会爆内存
        ctx.writeAndFlush(new ChunkedStream(fileInputStream));
    }
}
