package handler.server;

import io.netty.channel.*;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author SuanCaiYv
 * @time 2020/1/20 下午9:38
 */
public class OneIn extends ChannelInboundHandlerAdapter
{
    /**
     * 使用FileRegion来完成文件的写入, FileRegion支持零拷贝, 可以不通过内存就把文件传输到channel通道里
     * @param ctx NA
     * @throws Exception NA
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        File file = new File("/home/joker/IdeaProjects/Netty/chap11_exa4/src/main/java/static/IMG_0286.JPG");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileRegion fileRegion = new DefaultFileRegion(fileInputStream.getChannel(), 0, file.length());
        ChannelFuture future = ctx.channel().writeAndFlush(fileRegion);
        future.addListener(new ChannelFutureListener()
        {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception
            {
                if (future.isSuccess()) {
                    System.out.println("写入成功");
                }
                else {
                    System.out.println("写入失败");
                }
            }
        });
    }
}
