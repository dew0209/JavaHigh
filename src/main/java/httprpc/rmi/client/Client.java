package httprpc.rmi.client;

import httprpc.rmi.IOrder;

import java.rmi.Naming;

public class Client {
    public static void main(String[] args) throws Exception{
        IOrder iOrder = (IOrder)Naming.lookup("rmi://localhost:6666/order");
        System.out.println(iOrder.pay("1212"));
    }
}
