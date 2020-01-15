import tools.SSLContextGenerator;

import javax.net.ssl.SSLContext;

/**
 * @author SuanCaiYv
 * @time 2020/1/15 下午1:30
 */
public class Main
{
    public static void main(String[] args)
    {
        SSLContext serverContext = SSLContextGenerator.getServerContext();
        SSLContext clientContext = SSLContextGenerator.getClientContext();
        if (serverContext != null) System.out.println(1);
        if (clientContext != null) System.out.println(2);
    }
}
