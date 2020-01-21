package handler.server;

import java.io.*;
import java.nio.file.Files;

/**
 * @author SuanCaiYv
 * @time 2020/1/20 下午10:10
 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        File file1 = new File("/home/joker/IdeaProjects/Netty/chap11_exa4/src/main/java/static/IMG_0286.JPG");
        FileInputStream fileInputStream = new FileInputStream(file1);
        byte[] bytes = fileInputStream.readAllBytes();
        File file = new File("/home/joker/IdeaProjects/Netty/chap11_exa4/src/main/java/output/out.JPG");
        if (!Files.exists(file.toPath())) {
            Files.createFile(file.toPath());
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
    }
}
