package httprpc.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(8888);
        while (true){
            System.out.println("11++++++++++++++++++++++++++++++");
            Socket accept = socket.accept();
            new HttpThread(accept).start();
        }
    }
}

class HttpThread extends Thread{
    private final Socket socket;

    public HttpThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //接受到请求
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;
        try {
            System.out.println(socket);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);
            String len;
            int len1;
            byte buff[] = new byte[2000];
            while ((len1 = inputStream.read(buff)) != -1){
                System.out.println(len1);
            }
            System.out.println("end");
            outputStream = socket.getOutputStream();
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}