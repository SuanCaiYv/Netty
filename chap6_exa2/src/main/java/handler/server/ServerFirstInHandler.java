package handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author joker
 * @time 2019/12/16 下午8:42
 */
public class ServerFirstInHandler extends ChannelInboundHandlerAdapter
{
    private static final String ID = "ServerFirstInHandler: ";

    @Override
    public void channelRegistered(ChannelHandlerContext ctx)
    {
        System.out.println(ID+"连接被注册");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
    {
        System.out.println(ID+"连接处于活动状态");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
        System.out.println(ID+"开始读...");
        ByteBuf in = (ByteBuf) msg;
        System.out.println(ID+"读到了客户端说的: "+in.toString(CharsetUtil.UTF_8));
        ctx.write(Unpooled.copiedBuffer("我是服务端文本", CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
    {
        // 转发到下一个InboundHandler处理
        ctx.fireChannelReadComplete();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)
    {
        System.out.println(ID+"连接处于非活动状态");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx)
    {
        System.out.println(ID+"连接取消注册");
    }
}
