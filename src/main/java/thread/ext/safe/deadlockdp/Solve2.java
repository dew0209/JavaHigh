package thread.ext.safe.deadlockdp;

import java.util.Random;

public class Solve2 implements ITransfer{
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws Exception {
        Random r = new Random();
        while (true){
            if(from.getLock().tryLock()){
                try {
                    System.out.println(Thread.currentThread().getName() + "   " + from.getName());
                    if(to.getLock().tryLock()){
                        try{
                            System.out.println(Thread.currentThread().getName() + "   " + to.getName());
                            from.flyMoney(amount);
                            to.addMoney(amount);
                            break;
                        }finally {
                            to.getLock().unlock();
                        }
                    }
                }finally {
                    from.getLock().unlock();
                }
            }
            Thread.sleep(r.nextInt(10));
        }
    }
}
