import java.io.IOException;

/**
 * @author joker
 * @time 2019/12/12 下午9:29
 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        Reactor reactor = new Reactor();
        Thread thread = new Thread(reactor);
        thread.start();
    }
}
