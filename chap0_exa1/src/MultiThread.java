import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author joker
 * @time 2019/12/11 下午6:03
 */
public class MultiThread
{
    public static void main(String[] args) throws IOException
    {
        ReactorMulti reactorMulti = new ReactorMulti();
        reactorMulti.run();
    }
}
class ReactorMulti implements Runnable
{
    private static final int PORT = 8189;
    final Selector selector;
    final ServerSocketChannel serverSocketChannel;
    public ReactorMulti() throws IOException
    {
        this.selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new AcceptorMulti(selector, serverSocketChannel));
    }
    // 这个run()才是多线程的run()
    @Override
    public void run()
    {
        try {
            int cnt = 0;
            while (true) {
                System.out.println("第"+cnt+"次轮徇");
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    dispatcher(key);
                }
                ++cnt;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void dispatcher(SelectionKey selectionKey)
    {
        Runnable runnable = (Runnable) selectionKey.attachment();
        if (runnable != null) {
            runnable.run();
        }
    }
}
class AcceptorMulti implements Runnable
{
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    AcceptorMulti(Selector selector, ServerSocketChannel serverSocketChannel)
    {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }
    // 这里的run()就是一个普通方法,不是多线程那个!!!
    @Override
    public void run()
    {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                new HandlerMulti(selector, socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class HandlerMulti implements Runnable
{
    private ExecutorService executorService;
    private SocketChannel socketChannel;
    private SelectionKey selectionKey;
    private String msg;
    private static final int READ = 1, WRITE = 2;
    private int statue = READ;
    HandlerMulti(Selector selector, SocketChannel socketChannel) throws IOException
    {
        if (socketChannel == null) {
            System.out.println("socketChannel is null");
        }
        assert socketChannel != null;
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        this.selectionKey = socketChannel.register(selector, 0);
        this.selectionKey.attach(this);
        this.selectionKey.interestOps(SelectionKey.OP_READ);
        assert selector != null;
        executorService = Executors.newFixedThreadPool(10);
        selector.wakeup();
    }
    // 这里的run()就是一个普通方法,不是多线程那个!!!
    @Override
    public void run()
    {
        System.out.println(Thread.currentThread().getName()+": is working");
        try {
            if (this.statue == READ) {
                doRead();
            }
            if (this.statue == WRITE)
            {
                doWrite();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void doRead() throws IOException, ExecutionException, InterruptedException
    {
        Read read = new Read(selectionKey);
        FutureTask<String> futureTask = new FutureTask<>(read);
        executorService.submit(futureTask);
        // 假装异步了......
        msg = futureTask.get();
        statue = WRITE;
    }
    public void doWrite() throws IOException
    {
        Write write = new Write(selectionKey, msg);
        FutureTask<String> futureTask = new FutureTask<>(write);
        executorService.submit(futureTask);
        statue = READ;
    }
}
class Read<T> implements Callable<T>
{
    private static final int BYTE_SIZE = 1024;
    private SelectionKey selectionKey;
    public Read(SelectionKey selectionKey)
    {
        this.selectionKey = selectionKey;
    }
    @Override
    public T call() throws Exception
    {
        String msg = null;
        ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_SIZE);
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        long readLength = socketChannel.read(byteBuffer);
        while (readLength > 0) {
            byteBuffer.flip();
            byte[] data = byteBuffer.array();
            msg = new String(data);
            byteBuffer.clear();
            readLength = socketChannel.read(byteBuffer);
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
        return (T) msg;
    }
}
class Write<T> implements Callable<T>
{
    private static int BYTE_SIZE = 1024;
    private SelectionKey selectionKey;
    private String msg;
    public Write(SelectionKey selectionKey, String msg)
    {
        this.selectionKey = selectionKey;
        this.msg = msg;
    }
    @Override
    public T call() throws Exception
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_SIZE);
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        byteBuffer.put(msg.getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        byteBuffer.compact();
        selectionKey.interestOps(SelectionKey.OP_READ);
        return null;
    }
}

