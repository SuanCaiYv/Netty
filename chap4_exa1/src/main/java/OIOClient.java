import java.io.*;
import java.net.Socket;

/**
 * @author joker
 * @time 2019/12/11 下午2:03
 */
public class OIOClient
{
    public static void main(String[] args) throws IOException
    {
        Socket socket = new Socket("127.0.0.1", 8189);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
        System.out.println(bufferedReader.readLine());
    }
}
