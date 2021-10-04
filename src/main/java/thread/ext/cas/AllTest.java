package thread.ext.cas;

import java.util.concurrent.atomic.*;

/**
 * 原子操作：[不可分割]
 *    要么全部做完，要么不做
 *    synchronized基于阻塞的锁的机制，1.被阻塞的优先级很高 2.拿到锁的线程一直不释放
 *                                3.大量的竞争，消耗cpu，带来死锁其他的安全问题
 *    CAS原理[compare and swap]：利用现代处理器都支持的cas指令，循环这个指令，直到成功为止[利用硬件实现的]   CAS不是锁，CAS不是锁，CAS不是锁  但是线程是安全的，线程的安全的，线程是安全的[重要的事情说三遍]
 *          指令级别保证这是一个原子操作
 *          三个运算符：一个内存地址v，一个期望的值a，一个新的值b
 *                      检查内存v，如果和a相等，用b进行更新，如果不相等，则不进行更新操作[循环里面不断的进行cas操作，自旋(死循环)]
 *          CAS带来的问题：
 *              1.ABA：一个值开始为A，更新为B，然后又被更新为A，线程A获得期望值A，准备修改，此时线程B将其修改为B，后又修改回A，线程A继续操作，操作成功。解决这个问题：版本号
 *              2.只能带来一个共享变量的原子操作
 *
 *
 *
 *
 */
public class AllTest {
    private static AtomicInteger ai = new AtomicInteger(10);
    private static AtomicReference<UserInfo> users = new AtomicReference();
    private static AtomicStampedReference<String> reference = new AtomicStampedReference<>("mark",0);
    static int[] base = {1,2,3};
    private static AtomicIntegerArray arr = new AtomicIntegerArray(base);
    public static void main(String[] args) throws InterruptedException {
        /*//演示基本类型的原子操作类
        System.out.println(ai.getAndIncrement());//10
        System.out.println(ai.getAndIncrement());//11
        System.out.println(ai.incrementAndGet());//13
        //演示引用类型的原子操作类
        System.out.println("分割");
        UserInfo mark = new UserInfo("Mark", 15);
        users.set(mark);
        UserInfo deer = new UserInfo("deer", 17);
        users.compareAndSet(mark,deer);
        System.out.println(users.get());
        System.out.println(mark);//这个不会变，注意哦，有点深拷贝的意思
        //演示解决ABA问题，AtomicMarkableReference(boolean的版本戳，有没有动过)和AtomicStampedReference(动过几次)都是为了解决aba问题的
        System.out.println("华丽的分割线");*/
        final int stamp = reference.getStamp();//初始的版本号
        String re = AllTest.reference.getReference();//拿初始的值
        System.out.println(re + " debug " + stamp);
        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println(Thread.currentThread().getName() + ",当前变量值：" + re +
                         ",版本戳：" + stamp + " --> " + reference.compareAndSet(re,re + "java",stamp,stamp + 1));
            }
        }).start();
        Thread.sleep(1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String re1 = reference.getReference();
                System.out.println(Thread.currentThread().getName() + ",当前变量值：" + re1 +
                        ",版本戳：" + reference.getStamp() + " --> " + reference.compareAndSet(re,re + "C",stamp,stamp + 1));
            }
        }).start();
        Thread.sleep(1000);
        System.out.println(reference.getReference() + " debug " + reference.getStamp());

        System.out.println("华丽的分割线");
        //演示AtomicIntegerArray的使用
        arr.getAndSet(0,55);
        System.out.println(arr.get(0));//55
        System.out.println(base[0]);//不会发生变化  1  类似深拷贝

    }
    private static class UserInfo{
        private String name;
        private int age;

        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
