package handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

/**
 * @author joker
 * @time 2020/1/12 下午4:33
 */
public class OneOutboundHandler extends ChannelOutboundHandlerAdapter
{
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("Client写出: "+byteBuf.toString(CharsetUtil.UTF_8));
        // 不使用ctx进行写,则不会发生实际的写操作
        ctx.write(msg, promise);
        // pipeline()的flush()方法会调用下一个OutboundHandler的flush()方法
        ctx.pipeline().flush();
    }
}
