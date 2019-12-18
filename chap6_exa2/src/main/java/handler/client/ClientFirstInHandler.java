package handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author joker
 * @time 2019/12/16 下午8:42
 */
public class ClientFirstInHandler extends ChannelInboundHandlerAdapter
{
    private static final String ID = "ClientFirstInHandler: ";

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println(ID+"连接被注册");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        String msg = "客户端已连接到服务器";
        ByteBuf out = Unpooled.copiedBuffer(msg.getBytes());
        ctx.writeAndFlush(out);
        System.out.println(ID+"连接处于活动状态");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println(ID+"开始读...");
        ByteBuf in = (ByteBuf) msg;
        System.out.println(ID+"读到了服务器说的: "+in.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println(ID+"读取完成");
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println(ID+"连接处于非活动状态");
        ctx.flush();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println(ID+"连接取消注册");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        System.out.println(cause.getMessage());
    }
}
