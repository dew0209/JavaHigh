package thread.ext.concurrent;


import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 更多的并发容器
 *      ConcurrentSkipListMap和ConcurrentSkipListSet就是[TreeMap和TreeSet有序的容器]的并发版本
 *          SkipList：跳表
 *      ConcurrentLinkedDeque 无界非阻塞队列[先进先出]，底层是个链表
 *          add after将元素插入到尾部
 *          peek[拿但不移除] poll[拿且移除] 拿头部数据
 *       写时复制容器：
 *          只能保证最终一致性，读多写少的并发场景，对一致性要求高的场景不适用
 *          内存占用
 *          CopyOnWriteArraySet
 *          CopyOnWriteArrayList
 *        阻塞队列【可以理解为生产者和消费者模型】：
 *          当队列满的时候，插入元素的线程被阻塞，直到队列不满
 *          队列为空的时候，获取元素的线程被阻塞，直到队列不空
 *          常用方法：
 *              方法   抛出异常的   返回值    一直阻塞    超时退出
 *              插入      add     offer    put       offer
 *              移除      remove  poll     take      poll
 *              检查      element peek     N/A       N/A
 *          接口：java.util.concurrent.BlockingQueue
 *
 *
 */
public class MoreTest {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<Item<Order>> queue = new DelayQueue<>();
        Thread t1 = new Thread(new PutOrder(queue));
        t1.start();
        Thread t2 = new Thread(new Fetch(queue));
        t2.start();
        //每隔500毫秒，打印一个数字
        for(int i = 0;i < 20;i++){
            Thread.sleep(500);
            System.out.println(i * 500);
        }
    }

}

class Fetch implements Runnable{
    private DelayQueue<Item<Order>> queue;

    public Fetch(DelayQueue<Item<Order>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                Item<Order> take = queue.take();
                Order date = take.getDate();
                System.out.println(date);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class PutOrder implements Runnable{
    private DelayQueue<Item<Order>> queue;

    public PutOrder(DelayQueue<Item<Order>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Order order = new Order("Tb12345",366);
        Item<Order> item = new Item<>(5000,order);
        queue.offer(item);
        System.out.println("订单5秒后到期：" + order.getOrderNo());

        Order order1 = new Order("Tb12345",366);
        Item<Order> item1 = new Item<>(8000,order1);
        queue.offer(item1);
        System.out.println("订单8秒后到期：" + order1.getOrderNo());
    }
}

class Order{
    private final String orderNo;//订单的编号
    private final double orderMoney;//订单的金额

    public Order(String orderNo, double orderMoney) {
        this.orderNo = orderNo;
        this.orderMoney = orderMoney;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public double getOrderMoney() {
        return orderMoney;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo='" + orderNo + '\'' +
                ", orderMoney=" + orderMoney +
                '}';
    }
}
class Item<T> implements Delayed {

    private long activeTime;//到期时间  过期时长
    private T date;

    public Item(long activeTime, T date) {
        this.activeTime = TimeUnit.NANOSECONDS.convert(activeTime,
                TimeUnit.MILLISECONDS) + System.nanoTime();//将传入的时长转入为超时的时刻
        this.date = date;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public T getDate() {
        return date;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long d = unit.convert(this.activeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        return d;
    }

    @Override
    public int compareTo(Delayed o) {
        long delay = getDelay(TimeUnit.NANOSECONDS);
        long d1 = o.getDelay(TimeUnit.NANOSECONDS);
        long d = delay - d1;
        return d == 0 ? 0 : (d > 0 ? 1 : -1);
    }
}