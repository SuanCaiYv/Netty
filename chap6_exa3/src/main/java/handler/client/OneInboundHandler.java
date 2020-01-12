package handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author joker
 * @time 2020/1/12 下午4:33
 */
public class OneInboundHandler extends ChannelInboundHandlerAdapter
{
    private int counter = 0;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("连接处于活跃状态, 可以开始IO操作");
        ctx.writeAndFlush(Unpooled.copiedBuffer("qwer".getBytes()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("第"+counter+"次, 读到了"+byteBuf.toString(CharsetUtil.UTF_8));
        ++counter;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("第"+counter+"次读取完成");
    }
}