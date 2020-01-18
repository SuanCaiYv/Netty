package handler.server;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午11:24
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter
{
    private int count = 0;

    /**
     * 此方法的默认实现是转发给下一个: ctx.fireUserEventTriggered(evt); 所以最后会在这里被实际地处理
     * @param ctx NA
     * @param evt NA
     * @throws Exception NA
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 3秒没有读到东西, 触发此事件
            if (event.state().equals(IdleState.READER_IDLE)) {
                ++count;
                System.out.println("服务端已经3秒没有读取了, 您这是第"+count+"次看到此消息");
            }
            if (count > 5) {
                Channel channel = ctx.channel();
                ChannelFuture future = channel.close();
                System.out.println("已经5次没有读取到了, 准备关闭连接");
                future.addListener(new ChannelFutureListener()
                {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception
                    {
                        if (future.isSuccess()) {
                            System.out.println("已关闭Channel");
                        }
                        else {
                            System.out.println("连接关闭失败");
                        }
                    }
                });
            }
        }
        else {
            ctx.fireUserEventTriggered(evt);
        }
    }
}
