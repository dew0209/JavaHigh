package generic;

import org.omg.CORBA.Object;

/**
 * 自定义泛型类
 * @param <T>
 */
public class Order<T> {
    String orderName;
    int orderId;
    T orderT;
    private Order(){
        //编译不通过
        //T[] arr = new T[10];//Type parameter 'T' cannot be instantiated directly
        T[] arr = (T[])new Object[10];//ac
    }

    public Order(String orderName, int orderId, T orderT) {
        this.orderName = orderName;
        this.orderId = orderId;
        this.orderT = orderT;
    }

    public T getOrderT() {
        return orderT;
    }

    public void setOrderT(T orderT) {
        this.orderT = orderT;
    }

    public void show(){
        //编译不通过
       /* try {

        }catch (T t){

        }*/
    }
    @Override
    public String toString() {
        return "Order{" +
                "orderName='" + orderName + '\'' +
                ", orderId=" + orderId +
                ", orderT=" + orderT +
                '}';
    }
}
