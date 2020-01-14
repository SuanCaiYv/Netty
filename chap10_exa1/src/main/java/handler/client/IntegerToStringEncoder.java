package handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * POJO to POJO操作, 泛型是from类型
 * @author SuanCaiYv
 * @time 2020/1/15 上午12:00
 */
public class IntegerToStringEncoder extends MessageToMessageEncoder<Integer>
{
    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception
    {
        System.out.println("Integer转换成String");
        out.add(String.valueOf(msg));
    }
}
