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
 *      活锁：线程饥饿
 *      性能等
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
