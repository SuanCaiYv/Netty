import client.Client;
import server.Server;

/**
 * @author joker
 * @date 2020/1/13 下午8:26
 */
public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        Server server = new Server();
        Client client = new Client();
        server.run();
        Thread.sleep(100);
        client.run();
    }
}
