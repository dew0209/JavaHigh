package thread.ext.safe.deadlockdp;

public class TrasnferAccount implements ITransfer{
    //not a safe thread
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws Exception {
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
