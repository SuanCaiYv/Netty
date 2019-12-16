import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author joker
 * @time 2019/12/11 下午6:03
 */
public class MainSubstrThread
{
    public static void main(String[] args)
    {
        ;
    }
}
class ReactorMainSubstr implements Runnable
{
    private Selector[] selectors;
    private ExecutorService executorService;

    public ReactorMainSubstr() throws IOException
    {
        selectors = new Selector[2];
        selectors[0] = Selector.open();
        selectors[1] = Selector.open();
        this.executorService = Executors.newFixedThreadPool(5);
    }

    @Override
    public void run()
    {
        try {
            executorService.submit(new Accept(selectors));
            while (true) {
                try {
                    selectors[1].select();
                    Set<SelectionKey> keys = selectors[1].selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        dispatcher(selectionKey);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void dispatcher(SelectionKey selectionKey)
    {
        ;
    }
}
class Accept implements Runnable
{
    private ServerSocketChannel serverSocketChannel;
    private Selector[] selectors;
    private ExecutorService executorService;
    public Accept(Selector[] selectors) throws IOException
    {
        int PORT = 8189;
        this.selectors = selectors;
        this.executorService = Executors.newFixedThreadPool(20);
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(PORT));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selectors[0], SelectionKey.OP_ACCEPT);
    }
    @Override
    public void run()
    {
        while (true) {
            try {
                selectors[0].select();
                Set<SelectionKey> selectionKeys = selectors[0].selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    executorService.submit(new AcceptorMainSubstr(selectionKey, selectors[1]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
class AcceptorMainSubstr implements Runnable
{
    private SelectionKey selectionKey;
    private Selector selector;
    public AcceptorMainSubstr(SelectionKey selectionKey, Selector selector)
    {
        this.selectionKey = selectionKey;
        this.selector = selector;
    }
    @Override
    public void run()
    {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            new HandlerMainSubstr(selector, socketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class HandlerMainSubstr
{
    private Selector selector;
    private SocketChannel socketChannel;
    public HandlerMainSubstr(Selector selector, SocketChannel socketChannel)
    {
        this.selector = selector;
        this.socketChannel = socketChannel;
    }
}
