package generic;

//SubOrder1不是泛型类
public class SubOrder1 extends Order<Integer>{

    public SubOrder1(String orderName, int orderId, Integer orderT) {
        super(orderName, orderId, orderT);
    }
}
