package tools;

import io.netty.util.internal.SystemPropertyUtil;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * 此类用来获得TLS加密
 * @author SuanCaiYv
 * @time 2020/1/15 下午1:04
 */
public class SSLContextGenerator
{
    private static final String PROTOCOL = "TLSv1.2";
    private static final SSLContext SERVER_CONTEXT;
    private static String SERVER_KEY_STORE = "/home/joker/IdeaProjects/Netty/chap11_exa1/src/ssl/sslserverkeys";
    private static String SERVER_TRUST_KEY_STORE = "/home/joker/IdeaProjects/Netty/chap11_exa1/src/ssl/sslservertrust";
    private static String SERVER_KEY_STORE_PASSWORD = "461200";
    private static String SERVER_TRUST_KEY_STORE_PASSWORD = "461200";
    private static final SSLContext CLIENT_CONTEXT;
    private static String CLIENT_KEY_STORE = "/home/joker/IdeaProjects/Netty/chap11_exa1/src/ssl/sslclientkeys";
    private static String CLIENT_TRUST_KEY_STORE = "/home/joker/IdeaProjects/Netty/chap11_exa1/src/ssl/sslclienttrust";
    private static String CLIENT_KEY_STORE_PASSWORD = "461200";
    private static String CLIENT_TRUST_KEY_STORE_PASSWORD = "461200";
    static {
        String algorithm = SystemPropertyUtil.get("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SunX509";
        }
        System.out.println(algorithm);
        SSLContext serverContext = null;
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(SERVER_KEY_STORE), SERVER_KEY_STORE_PASSWORD.toCharArray());
            KeyStore tks = KeyStore.getInstance("JKS");
            tks.load(new FileInputStream(SERVER_TRUST_KEY_STORE), SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
            tmf.init(tks);
            serverContext = SSLContext.getInstance(PROTOCOL);
            serverContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SERVER_CONTEXT = serverContext;
    }
    static {
        String algorithm = SystemPropertyUtil.get("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SunX509";
        }
        System.out.println(algorithm);
        SSLContext clientContext = null;
        try {
            KeyStore ks2 = KeyStore.getInstance("JKS");
            ks2.load(new FileInputStream(CLIENT_KEY_STORE), CLIENT_KEY_STORE_PASSWORD.toCharArray());
            KeyStore tks2 = KeyStore.getInstance("JKS");
            tks2.load(new FileInputStream(CLIENT_TRUST_KEY_STORE), CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
            KeyManagerFactory kmf2 = KeyManagerFactory.getInstance(algorithm);
            TrustManagerFactory tmf2 = TrustManagerFactory.getInstance("SunX509");
            kmf2.init(ks2, CLIENT_KEY_STORE_PASSWORD.toCharArray());
            tmf2.init(tks2);
            clientContext = SSLContext.getInstance(PROTOCOL);
            clientContext.init(kmf2.getKeyManagers(), tmf2.getTrustManagers(), null);
        } catch (Exception e) {
            throw new Error(e);
        }
        CLIENT_CONTEXT = clientContext;
    }
    public static SSLContext getServerContext()
    {
        return SERVER_CONTEXT;
    }
    public static SSLContext getClientContext()
    {
        return CLIENT_CONTEXT;
    }
}
