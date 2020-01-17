package handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

/**
 * @author SuanCaiYv
 * @time 2020/1/17 下午4:36
 */
public class ByteToStringCodec extends ByteToMessageCodec<String>
{
    private static final int MAX_FRAME_SIZE = 1024;
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception
    {
        out.writeBytes(msg.getBytes());
        ReferenceCountUtil.release(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        while (in.isReadable()) {
            int outputSizeBefore = out.size();
            int readableBytesBefore = in.readableBytes();
            if (readableBytesBefore > MAX_FRAME_SIZE) {
                in.skipBytes(readableBytesBefore);
                throw new TooLongFrameException("Frame Too Long");
            }
            byte[] bytes = new byte[readableBytesBefore];
            in.duplicate().readBytes(bytes);
            in.skipBytes(readableBytesBefore);
            String str = new String(bytes);
            out.add(str);
            int outputSizeAfter = out.size();
            int readableBytesAfter = in.readableBytes();
            boolean didNotDecodeAnything = (outputSizeBefore == outputSizeAfter);
            boolean didNotReadAnything = (readableBytesBefore == readableBytesAfter);
            if(didNotDecodeAnything && didNotReadAnything) {
                break;
            }
        }
    }
}
