package httprpc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IOrder extends Remote {
    public String pay(String id) throws RemoteException;
}
