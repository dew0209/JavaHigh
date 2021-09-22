package httprpc.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception{
        ServerSocket socket = new ServerSocket(8888);

        while (true){
            Socket accept = socket.accept();
            //System.out.println(accept);
            InputStream ops = accept.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(ops);
            //BufferedReader reader = new BufferedReader(new InputStreamReader(ops));
            ByteArrayOutputStream inputStream = new ByteArrayOutputStream();
            byte buff[] = new byte[2000];
            int len;
            while ((len = bis.read(buff)) != -1 ){
                System.out.println(len);
            }
            //获得传过来的信息
            System.out.println(inputStream.toString());
            //做出响应
            OutputStream outputStream = accept.getOutputStream();
            StringBuffer sb = new StringBuffer();
            sb.append("HTTP/1.1 200 OK \r\n");
            sb.append("Content-Type: text/html \r\n");
            sb.append("\r\n");
            sb.append("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Title</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    你好啊！！\n" +
                    "</body>\n" +
                    "</html>");
            outputStream.write(sb.toString().getBytes());

            ops.close();
            bis.close();
            outputStream.close();
            accept.close();
        }

    }
}
