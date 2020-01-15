package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

/**
 * 注意解/编码器放置的位置, 一般在"中间"
 * 前面的是INBOUND_IN, 后面的是OUTBOUND_IN
 * @author SuanCaiYv
 * @time 2020/1/15 上午12:38
 */
public class StringToIntegerCodec extends MessageToMessageCodec<String, Integer>
{
    /**
     * 编码器, 后面的编为前面的
     * @param ctx NA
     * @param msg NA
     * @param out NA
     * @throws Exception NA
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception
    {
        out.add(String.valueOf(msg));
        ReferenceCountUtil.release(msg);
    }

    /**
     * 解码器, 前面的解码为后面的
     * @param ctx NA
     * @param msg NA
     * @param out NA
     * @throws Exception NA
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception
    {
        out.add(Integer.parseInt(msg));
        ReferenceCountUtil.release(msg);
    }
}
