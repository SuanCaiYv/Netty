package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author joker
 * @time 2019/12/16 下午8:04
 */
public class SecondClientInHandler extends SecondInHandler
{
    private static final String ID = "SecondClientInHandler";
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        super.channelRead(ctx, msg);
        ByteBuf in = (ByteBuf) msg;
        System.out.println(ID+": Client received: "+in.toString());
        ctx.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        super.channelReadComplete(ctx);
        ctx.writeAndFlush("").addListener(ChannelFutureListener.CLOSE);
    }
}
