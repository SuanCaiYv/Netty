package handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.http.*;

import java.io.RandomAccessFile;

/**
 * ctx的write把消息写进任务队列, 不刷新到缓冲区, writeAndFlush写到任务队列并刷新到缓冲区, 进而可以刷新到socketChannel
 * @author SuanCaiYv
 * @time 2020/2/5 下午11:49
 */
public class HttpRequestHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        HttpObject httpObject = (HttpObject) msg;
        HttpRequest request = (HttpRequest) httpObject;
        RandomAccessFile file = new RandomAccessFile("/home/joker/IdeaProjects/Netty/chap12_exa2/src/main/resources/static/index.html", "r");
        FileRegion fileRegion = new DefaultFileRegion(file.getChannel(), 0, file.length());
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, file.length());
        response.headers().set("file-name", "index");
        System.out.println(request.uri());
        ctx.write(response);
        ctx.write(fileRegion);
        ctx.write(FullHttpResponse.EMPTY_LAST_CONTENT);
    }
}
