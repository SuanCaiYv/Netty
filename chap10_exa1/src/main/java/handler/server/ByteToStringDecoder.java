package handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * 解码器属于入站操作
 * @author SuanCaiYv
 * @time 2020/1/14 下午5:22
 */
public class ByteToStringDecoder extends ByteToMessageDecoder
{
    // 设置最大帧大小
    private static final int MAX_FRAME_SIZE = 1024;
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
        // out数据会传递到下一个Handler,在那里作为msg出现
    }

    /**
     * 此方法在channel状态改变(变为非活动)时被调用,常用来进行特殊处理
     * @param ctx NA
     * @param in NA
     * @param out NA
     * @throws Exception NA
     */
    @Override
    protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        System.out.println("解码完成");
    }
}
