package handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @author joker
 * @time 2020/1/12 下午4:34
 */
public class OneInboundHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ByteBuf byteBuf;
        String str = "";
        while (!"exit".equals(str)) {
            byteBuf = (ByteBuf) msg;
            str = byteBuf.toString(CharsetUtil.UTF_8);
            System.out.println("Server读取到了: "+str);
            ctx.writeAndFlush(Unpooled.copiedBuffer(("服务端: "+str).getBytes()));
            ReferenceCountUtil.release(msg);
        }
        ctx.channel().closeFuture();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("读取完成");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("连接处于活跃状态, 可以开始IO操作");
        ctx.write(Unpooled.copiedBuffer("我是服务端".getBytes()));
    }
}
