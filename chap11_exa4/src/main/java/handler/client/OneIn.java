package handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.FileRegion;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

/**
 * @author SuanCaiYv
 * @time 2020/1/20 下午9:38
 */
public class OneIn extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        File file = new File("/home/joker/IdeaProjects/Netty/chap11_exa4/src/main/java/output/out.JPG");
        if (!Files.exists(file.toPath())) {
            Files.createFile(file.toPath());
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
    }
}
