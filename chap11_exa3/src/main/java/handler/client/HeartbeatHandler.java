package handler.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 心跳机制具体流程, 设置心跳时间, 监听Channel, 如果时间内一直没有发生某一指定的事件, 到时间了会触发一个userEventTriggered()方法, 此方法的evt参数包含触发的事件具体是什么事件
 * 然后会按入站流向进行依次传递, 直到遇到可以处理userEventTriggered()方法的地方, 再进行细节处理
 * @author SuanCaiYv
 * @time 2020/1/17 下午4:51
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter
{
    private int count = 0;

    /**
     * 加入了心跳机制, 如果4秒没有"写活动"那么就会触发HeartbeatHandler
     * @param ctx NA
     * @param evt NA
     * @throws Exception NA
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        String msg = "text";
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 如果4秒都没有"写", 那么会触发userEventTriggered()方法, 然后判断是什么事件
            if (event.state().equals(IdleState.WRITER_IDLE)) {
                ++count;
                System.out.println("已经4秒没有写消息了, 这是您第"+count+"次看到此消息");
                System.out.println("准备写出消息");
                ctx.write(msg);
            }
            if (count > 5) {
                System.out.println("已经5次没有写出消息了, 准备关闭连接");
                ChannelFuture future = ctx.channel().close();
                future.addListener(new ChannelFutureListener()
                {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception
                    {
                        if (future.isSuccess()) {
                            System.out.println("Channel连接已关闭");
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
