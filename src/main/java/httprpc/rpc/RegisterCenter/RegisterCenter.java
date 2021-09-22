package httprpc.rpc.RegisterCenter;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterCenter {
    //线程池
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    private static final HashMap<String,Class> serviceRegistry = new HashMap<>();
    private static boolean isRunning = false;

    private static int port;

    public RegisterCenter(int port) {
        this.port = port;
    }
    public void start() throws IOException{
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        System.out.println("start server");
        try {
            while (true){
                executor.execute(new ServiceTask(server.accept()));
            }
        } finally {
            server.close();
        }
    }
    //服务的注册：socket通讯+反射
    public void register(Class serviceInterface,Class impl){
        serviceRegistry.put(serviceInterface.getName(),impl);
    }
    //服务的获取运行
    private static class ServiceTask implements Runnable{
        private Socket client = null;

        public ServiceTask(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            //书写代码
            ObjectInputStream objectInputStream = null;
            ObjectOutputStream objectOutputStream = null;
            try{
                InputStream inputStream = client.getInputStream();
                objectInputStream = new ObjectInputStream(inputStream);
                String serviceName = objectInputStream.readUTF();
                String methodName = objectInputStream.readUTF();
                System.out.println(serviceName + " --- " + methodName);
                Class<?>[] paramTypes = (Class<?>[])objectInputStream.readObject();
                Object[] o = (Object[]) objectInputStream.readObject();
                Class serviceClass = serviceRegistry.get(serviceName);
                Method method = serviceClass.getMethod(methodName, paramTypes);
                //反射调用
                Object invoke = method.invoke(serviceClass.newInstance(), o);
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                objectOutputStream.writeObject(invoke);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
