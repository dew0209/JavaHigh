package thread.ext.safe;

/**
 * 类的线程的安全的定义
 *      如果多线程下使用这个类，不管多线程如何使用和调度这个类，这个类总是表示出正确的行为，这个类就是线程安全的
 *      操作的原子性，内存的可见性
 *      会在多个线程之间共享状态的时候，就会出现线程不安全
 *  怎么才能做到类的线程安全
 *      栈封闭：变量声明在方法内部即可，仅在该方法中使用即可
 *      无状态：没有任何成员变量的类
 *      让类不可见：1.让状态不可变,对于一个类，所有的成员变量应该是私有的，同样的只要有可能，所有的成员变量应该加上final进行修饰。2.也可以不加final，不允许修改内部属性的结构
 *      volatile：保证类的可见性，适合一个线程写，多个线程读
 *      加锁和cas：
 *      安全的发布：
 *      ThreadLocal：
 *  线程不安全引发的问题：
 *      死锁：获取锁的顺序不一致
 *      活锁：
 *      线程饥饿[低优先级的线程，总是拿不到执行时间]
 *      性能和思考：使用并发的目标是为了提高性能，引入多线程后，引入额外的开销，衡量应用程序的性能：服务时间，延迟时间，吞吐量，可伸缩性
 *          做实际的应用：1.先保证程序正确，再提高速度(黄金原则) 2.一定要以测试为基准
 *                一个应用程序里面，串行的部分是永远都有的
 *          影响性能的因素：
 *              上下文切换
 *              内存同步
 *              减少锁的竞争
 *              缩小锁的范围[粒度]
 *              避免的多余的锁缩减的范围
 *              替换独占锁：使用读写锁，CAS，使用系统提供的并发容器
 */
public class AllTest {
    public static void main(String[] args) {

    }
    private final int a;//安全
    private final int b;//安全
    private final Ref ref;//不安全，内部属性加上final就行了

    public AllTest(int a, int b,Ref ref) {
        this.a = a;
        this.ref = ref;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
    public static class Ref{
        private final int age;//加上final就行了

        public Ref(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }
    }
}
