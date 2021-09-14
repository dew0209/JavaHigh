package generic;

//SubOrder<T> 仍然是泛型类
public class SubOrder<T> extends Order<T>{
    public SubOrder(String orderName, int orderId, T orderT) {
        super(orderName, orderId, orderT);
    }
}
