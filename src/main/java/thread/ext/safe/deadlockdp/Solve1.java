package thread.ext.safe.deadlockdp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Solve1 implements ITransfer{
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws Exception {
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);
        if(fromHash < toHash){
            synchronized (from){
                System.out.println(Thread.currentThread().getName() + "   " + from.getName());
                Thread.sleep(1000);
                synchronized (to){
                    System.out.println(Thread.currentThread().getName() + "   " + to.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }
        }else if(fromHash > toHash){
            synchronized (to){
                System.out.println(Thread.currentThread().getName() + "   " + to.getName());
                Thread.sleep(1000);
                synchronized (from){
                    System.out.println(Thread.currentThread().getName() + "   " + from.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }
        }else {

            Object objLock = new Object();//加时赛
            synchronized (objLock){
                synchronized (from){
                    System.out.println(Thread.currentThread().getName() + "  get " + from.getName());
                    Thread.sleep(1000);
                    synchronized (to){
                        System.out.println(Thread.currentThread().getName() + "  to " + to.getName());
                        from.flyMoney(amount);
                        to.addMoney(amount);
                    }
                }
            }
        }
    }
}
