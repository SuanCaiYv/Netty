package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * ctx的write把消息写进任务队列, 不刷新到缓冲区, writeAndFlush写到任务队列并刷新到缓冲区, 进而可以刷新到socketChannel
 * @author SuanCaiYv
 * @time 2020/2/5 下午11:49
 */
public class HttpRequestHandler extends ChannelInboundHandlerAdapter
{
    private File index;
    private final String wsUri;
    public HttpRequestHandler(String wsUri)
    {
        index = new File("/home/joker/IdeaProjects/Netty/chap12_exa1/src/main/resources/static/index.html");
        this.wsUri = wsUri;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        HttpObject httpObject = (HttpObject) msg;
        FullHttpRequest request = (FullHttpRequest) httpObject;
        if (wsUri.equalsIgnoreCase(request.uri())) {
            ctx.fireChannelRead(request);
        }
        else {
            if (HttpUtil.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }
            RandomAccessFile file = new RandomAccessFile(index, "r");
            HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, file.length());
            response.headers().set("file-name", "index");
            System.out.println(request.uri());
            boolean keepAlive = HttpUtil.isKeepAlive(request);
            if (keepAlive) {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            ctx.write(LastHttpContent.EMPTY_LAST_CONTENT);
        }
    }
    public void send100Continue(ChannelHandlerContext ctx)
    {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        ctx.writeAndFlush(response);
    }
}
