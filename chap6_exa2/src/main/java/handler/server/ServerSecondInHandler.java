package handler.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author joker
 * @time 2019/12/18 下午9:59
 */
public class ServerSecondInHandler extends ChannelInboundHandlerAdapter
{
    private static final String ID = "ServerSecondInHandler: ";
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println(ID+"读取完成, 准备写入");
        ctx.write(Unpooled.EMPTY_BUFFER).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("写入成功");
            }
            else {
                System.out.println("写入失败");
            }
        });
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端写入完成", CharsetUtil.UTF_8));
    }
}
