package handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.FileRegion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author SuanCaiYv
 * @time 2020/1/20 下午9:38
 */
public class OneIn extends ChannelInboundHandlerAdapter
{
    private FileOutputStream fileOutputStream;
    public OneIn() throws IOException
    {
        File file = new File("/home/joker/IdeaProjects/Netty/chap11_exa4/src/main/java/output/out.JPG");
        if (!Files.exists(file.toPath())) {
            Files.createFile(file.toPath());
        }
        this.fileOutputStream = new FileOutputStream(file);
    }

    /**
     * 接收文件采用了分块接受并分块写入的方法(虽然在这个实例中可能不要分块)
     * 有一点需要注意, 就是, ByteBuf在整个channel生命周期内, 都是同一个, 所以要记得移动已读指针
     * @param ctx NA
     * @param msg NA
     * @throws Exception NA
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ByteBuf byteBuf = (ByteBuf) msg;
        int readableBytesBefore = byteBuf.readableBytes();
        byte[] bytes = new byte[readableBytesBefore];
        byteBuf.duplicate().readBytes(bytes);
        // 移动已读指针
        byteBuf.skipBytes(readableBytesBefore);
        fileOutputStream.write(bytes, 0, bytes.length);
        fileOutputStream.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        fileOutputStream.close();
    }
}
