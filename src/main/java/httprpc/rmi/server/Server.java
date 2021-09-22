package httprpc.rmi.server;

import httprpc.rmi.IOrder;
import httprpc.rmi.impl.OrderImpl;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) throws Exception {
        IOrder iOrder = new OrderImpl();
        //本地的服务注册到6666端口中
        LocateRegistry.createRegistry(6666);
        //把刚才的实例绑定到本地端口上的一个路径上
        Naming.bind("rmi://localhost:6666/order",iOrder);
        System.out.println("服务已经启动");
    }
}
