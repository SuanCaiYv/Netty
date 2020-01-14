package handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * 注意解/编码器被放置的位置, 应该为"中间"
 * @author SuanCaiYv
 * @time 2020/1/15 上午12:35
 */
public class ByteToStringCodec extends ByteToMessageCodec<String>
{
    private static final int MAX_FRAME_SIZE = 1024;
    /**
     * 出站操作时被调用
     * @param ctx NA
     * @param msg NA
     * @param out NA
     * @throws Exception NA
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception
    {
        out.writeBytes(msg.getBytes());
    }

    /**
     * 入站操作时被调用
     * @param ctx NA
     * @param in NA
     * @param out NA
     * @throws Exception NA
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        // 这是一个通过循环读取来实现解码的程序
        while (in.isReadable()) {
            int outputSizeBefore = out.size();
            int readableBytesBefore = in.readableBytes();
            // 如果帧大小过大,抛出异常
            if (readableBytesBefore > MAX_FRAME_SIZE) {
                in.skipBytes(readableBytesBefore);
                throw new TooLongFrameException("Frame Too Long");
            }
            byte[] bytes = new byte[readableBytesBefore];
            in.duplicate().readBytes(bytes);
            in.skipBytes(readableBytesBefore);
            String str = new String(bytes);
            System.out.println("转换成String为: "+str);
            out.add(str);
            int outputSizeAfter = out.size();
            int readableBytesAfter = in.readableBytes();
            boolean didNotDecodeAnything = (outputSizeBefore == outputSizeAfter);
            boolean didNotReadAnything = (readableBytesBefore == readableBytesAfter);
            // 如果同为false,既添加了元素且可读字节改变了,就跳出循环
            if(didNotDecodeAnything && didNotReadAnything) {
                break;
            }
        }
    }

    /**
     * 此方法用于channel处于非活动状态后的特殊处理
     * @param ctx NA
     * @param in NA
     * @param out NA
     * @throws Exception NA
     */
    @Override
    protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        System.out.println("解/编码完成");
    }
}
