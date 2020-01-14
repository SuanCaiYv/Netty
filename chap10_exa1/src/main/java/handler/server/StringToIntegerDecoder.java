package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * POJO to POJO的解码器,泛型是from类型
 * @author SuanCaiYv
 * @time 2020/1/14 下午5:22
 */
public class StringToIntegerDecoder extends MessageToMessageDecoder<String>
{
    /**
     * 此解码器只有这一个方法
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception
    {
        Integer num = Integer.parseInt(msg);
        out.add(num);
        System.out.println("转换成Integer为: "+num);
    }
}
