import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author joker
 * @time 2019/12/12 下午7:38
 */
public class SingleThread
{
    public static void main(String[] args) throws IOException
    {
        Reactor reactor = new Reactor();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(reactor);
    }
}
class Reactor implements Runnable
{
    private static final int PORT = 8189;
    final Selector selector;
    final ServerSocketChannel serverSocketChannel;
    Reactor() throws IOException
    {
        this.selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor(selector, serverSocketChannel));
    }
    @Override
    public void run()
    {
        try {
            while (true) {
                selector.select();
                Set<SelectionKey> interestKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : interestKeys) {
                    dispatcher(selectionKey);
                }
                interestKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void dispatcher(SelectionKey selectionKey)
    {
        Thread thread = new Thread((Runnable) selectionKey.attachment());
        thread.start();
    }
}
class Acceptor implements Runnable
{
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    Acceptor(Selector selector, ServerSocketChannel serverSocketChannel)
    {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }
    @Override
    public void run()
    {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                new Handler(selector, socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Handler implements Runnable
{
    private static final int BYTE_SIZE = 1024;
    private SocketChannel socketChannel;
    private SelectionKey selectionKey;
    private String msg;
    private static final int READ = 1, WRITE = 2;
    private int statue = READ;
    Handler(Selector selector, SocketChannel socketChannel) throws IOException
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
        selector.wakeup();
    }
    @Override
    public void run()
    {
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
    public void doRead() throws IOException
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_SIZE);
        long readLength = socketChannel.read(byteBuffer);
        while (readLength > 0) {
            byteBuffer.flip();
            byte[] data = byteBuffer.array();
            msg = new String(data);
            byteBuffer.clear();
            readLength = socketChannel.read(byteBuffer);
        }
        System.out.println("Client says: "+msg);
        this.selectionKey.interestOps(SelectionKey.OP_WRITE);
        this.statue = WRITE;
    }
    public void doWrite() throws IOException
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_SIZE);
        byteBuffer.put(msg.getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        byteBuffer.compact();
        selectionKey.interestOps(SelectionKey.OP_READ);
        this.statue = READ;

    }
}
