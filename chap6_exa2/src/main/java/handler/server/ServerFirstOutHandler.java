package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * @author joker
 * @time 2019/12/18 下午3:22
 */
public class ServerFirstOutHandler extends ChannelOutboundHandlerAdapter
{
    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception
    {
        System.out.println("服务器已绑定到端口");
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception
    {
        System.out.println("操作流已关闭");
    }
}
