package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * @author joker
 * @time 2019/12/16 下午8:34
 */
public class SecondClientOutHandler extends SecondOutHandler
{
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        super.write(ctx, msg, promise);
        msg = new String("I'm A Text");
        ctx.write(msg);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception
    {
        super.flush(ctx);
        ctx.flush();
    }
}
