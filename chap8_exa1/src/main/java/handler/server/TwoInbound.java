package handler.server;

import client.ChannelInitializerImpl0;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * @author joker
 * @date 2020/1/13 下午8:18
 */
public class TwoInbound extends ChannelInboundHandlerAdapter
{
    /**
     * 在服务中启动另一个客户端连接,并利用当前EventLoopgroup设置其Loopgroup以减少上下文切换的开支
     * @param ctx NA
     * @throws Exception NA
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.remoteAddress(new InetSocketAddress("localhost", 8190))
                .handler(new ChannelInitializerImpl0())
                .channel(EpollSocketChannel.class);
        // 关键之处
        bootstrap.group(ctx.channel().eventLoop());
        ChannelFuture future = bootstrap.connect();
        future.addListener(new ChannelFutureListener()
        {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception
            {
                if (channelFuture.isSuccess()) {
                    System.out.println("Connected");
                }
                else {
                    System.out.println("Error!");
                }
            }
        });
        future.channel().closeFuture();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
    }
}
