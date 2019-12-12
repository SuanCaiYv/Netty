import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
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
    class MthreadReactor implements Runnable
    {

        //subReactors集合, 一个selector代表一个subReactor
        Selector[] selectors=new Selector[2];
        int next = 0;
        final ServerSocketChannel serverSocket;

        MthreadReactor(int port) throws IOException
        { //Reactor初始化
            selectors[0]=Selector.open();
            selectors[1]= Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(port));
            //非阻塞
            serverSocket.configureBlocking(false);


            //分步处理,第一步,接收accept事件
            SelectionKey sk =
                    serverSocket.register( selectors[0], SelectionKey.OP_ACCEPT);
            //attach callback object, Acceptor
            sk.attach(new Acceptor());
        }

        public void run()
        {
            try
            {
                while (!Thread.interrupted())
                {
                    for (int i = 0; i <2 ; i++)
                    {
                        selectors[i].select();
                        Set selected =  selectors[i].selectedKeys();
                        Iterator it = selected.iterator();
                        while (it.hasNext())
                        {
                            //Reactor负责dispatch收到的事件
                            dispatch((SelectionKey) (it.next()));
                        }
                        selected.clear();
                    }

                }
            } catch (IOException ex)
            { /* ... */ }
        }

        void dispatch(SelectionKey k)
        {
            Runnable r = (Runnable) (k.attachment());
            //调用之前注册的callback对象
            if (r != null)
            {
                r.run();
            }
        }


        class Acceptor { // ...
            public synchronized void run() throws IOException
            {
                SocketChannel connection =
                        serverSocket.accept(); //主selector负责accept
                if (connection != null)
                {
                    new MthreadHandler(selectors[next], connection); //选个subReactor去负责接收到的connection
                }
                if (++next == selectors.length) {
                    next = 0;
                }
            }
        }
    }
    class MthreadHandler implements Runnable
    {
        final SocketChannel channel;
        final SelectionKey selectionKey;
        ByteBuffer input = ByteBuffer.allocate(1024);
        ByteBuffer output = ByteBuffer.allocate(1024);
        static final int READING = 0, SENDING = 1;
        int state = READING;


        ExecutorService pool = Executors.newFixedThreadPool(2);
        static final int PROCESSING = 3;

        MthreadHandler(Selector selector, SocketChannel c) throws IOException
        {
            channel = c;
            c.configureBlocking(false);
            // Optionally try first read now
            selectionKey = channel.register(selector, 0);

            //将Handler作为callback对象
            selectionKey.attach(this);

            //第二步,注册Read就绪事件
            selectionKey.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        }

        boolean inputIsComplete()
        {
            /* ... */
            return false;
        }

        boolean outputIsComplete()
        {

            /* ... */
            return false;
        }

        void process()
        {
            /* ... */
            return;
        }

        @Override
        public void run()
        {
            try
            {
                if (state == READING)
                {
                    read();
                }
                else if (state == SENDING)
                {
                    send();
                }
            } catch (IOException ex)
            { /* ... */ }
        }


        synchronized void read() throws IOException
        {
            // ...
            channel.read(input);
            if (inputIsComplete())
            {
                state = PROCESSING;
                //使用线程pool异步执行
                pool.execute(new Processer());
            }
        }

        void send() throws IOException
        {
            channel.write(output);

            //write完就结束了, 关闭select key
            if (outputIsComplete())
            {
                selectionKey.cancel();
            }
        }

        synchronized void processAndHandOff()
        {
            process();
            state = SENDING;
            // or rebind attachment
            //process完,开始等待write就绪
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }

        class Processer implements Runnable
        {
            @Override
            public void run()
            {
                processAndHandOff();
            }
        }

    }
}
