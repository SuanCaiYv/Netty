import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author joker
 * @time 2019/12/11 下午2:03
 */
public class OIOServer
{
    public static void main(String[] args) throws InterruptedException
    {
        String msg = "I'm Oio server";
        ByteBuf byteBuf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(msg, Charset.forName("UTF-8")));
        EventLoopGroup group = new OioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(group)
                .channel(OioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(8189))
                .childHandler(new ChannelInitializer<SocketChannel>()
                {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception
                    {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception
                            {
                                ctx.writeAndFlush(byteBuf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind().sync();
        channelFuture.channel().closeFuture().sync();
        group.shutdownGracefully().sync();
    }
}
