public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        Server server = new Server();
        Client client = new Client();
        server.run();
        client.run();
    }
}
