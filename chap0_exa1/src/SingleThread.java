import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author joker
 * @time 2019/12/11 下午6:02
 */
public class SingleThread
{
    public static void main(String[] args) throws IOException
    {
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(8189));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                if (selectionKey.isAcceptable()) {
                    executorService.submit(new Handler<>(selectionKey.selector()));
                }
            }
        }
    }
}
class Handler<T> implements Callable<T>
{
    private T data;
    private Selector selector;
    public Handler(Selector selector)
    {
        this.selector = selector;
    }
    @Override
    public T call() throws Exception
    {
        int cnt = 0;
        while (cnt < 5) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isReadable()) {
                    doRead(selectionKey);
                }
                if (selectionKey.isWritable()) {
                    doWrite(selectionKey);
                }
            }
            ++cnt;
        }
        String msg = "Done!";
        data = (T) msg;
        return data;
    }
    public void doRead(SelectionKey selectionKey) throws IOException
    {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        long readLength = channel.read(byteBuffer);
        String msg = null;
        while (readLength > 0) {
            byteBuffer.flip();
            byte[] data = byteBuffer.array();
            msg = new String(data).trim();
            System.out.println("Client says: "+msg);
            byteBuffer.clear();
            readLength = channel.read(byteBuffer);
        }
        channel.configureBlocking(false);
        channel.register(selectionKey.selector(), SelectionKey.OP_WRITE);
    }
    public void doWrite(SelectionKey selectionKey) throws IOException
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        String msg = "qwer";
        byteBuffer.put(msg.getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
        byteBuffer.compact();
        channel.configureBlocking(false);
        channel.register(selectionKey.selector(), SelectionKey.OP_READ);
    }
}
