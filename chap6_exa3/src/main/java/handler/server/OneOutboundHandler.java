package handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

/**
 * @author joker
 * @time 2020/1/12 下午4:34
 */
public class OneOutboundHandler extends ChannelOutboundHandlerAdapter
{
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("Server写出: "+byteBuf.toString(CharsetUtil.UTF_8));
        ctx.write(msg, promise);
        ctx.pipeline().flush();
    }
}
