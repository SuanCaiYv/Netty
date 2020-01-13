package handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.Scanner;

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
        ctx.write(Unpooled.copiedBuffer("我是客户端".getBytes()));
        String str = "";
        Scanner scanner = new Scanner(System.in);
        while (!"exit".equals(str)) {
            str = scanner.nextLine();
            ctx.writeAndFlush(Unpooled.copiedBuffer(str.getBytes()));
        }
        ctx.channel().closeFuture();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ByteBuf byteBuf;
        String str = "";
        while (!"exit".equals(str)) {
            byteBuf = (ByteBuf) msg;
            str = byteBuf.toString(CharsetUtil.UTF_8);
            System.out.println("客户端读到了: "+str);
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("第"+counter+"次读取完成");
        ++counter;
    }
}
