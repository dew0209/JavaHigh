package httprpc.rmi.impl;

import httprpc.rmi.IOrder;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class OrderImpl extends UnicastRemoteObject implements IOrder {
    public OrderImpl() throws RemoteException {
        super();
    }

    @Override
    public String pay(String id) {
        return "支付成功！商品订单号：" + id;
    }
}
