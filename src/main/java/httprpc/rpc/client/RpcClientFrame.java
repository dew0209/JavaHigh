package httprpc.rpc.client;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RpcClientFrame {
    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface) throws Exception{
        InetSocketAddress serviceAddr = new InetSocketAddress("127.0.0.1",8888);
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(),new Class[]{serviceInterface},new DynProxy(serviceInterface,serviceAddr));
    }
    private static class DynProxy implements InvocationHandler{

        private final Class<?> serviceInterface;
        private final InetSocketAddress addr;

        public DynProxy(Class<?> serviceInterface, InetSocketAddress addr) {
            this.serviceInterface = serviceInterface;
            this.addr = addr;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Socket socket = null;
            ObjectInputStream objectInputStream = null;
            ObjectOutputStream objectOutputStream = null;
            try{
                socket = new Socket();
                //连接到远程的地址和端口
                socket.connect(addr);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeUTF(serviceInterface.getName());
                objectOutputStream.writeUTF(method.getName());
                objectOutputStream.writeObject(method.getParameterTypes());
                objectOutputStream.writeObject(args);
                objectOutputStream.flush();

                InputStream inputStream = socket.getInputStream();
                objectInputStream = new ObjectInputStream(inputStream);
                System.out.println("远程调用成功！！" + serviceInterface.getName());

                return objectInputStream.readObject();
            }finally {
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
            }
        }
    }
}
