package thread.ext.real_example;



import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class CheckJobProcesser {
    private static DelayQueue<ItemVo<String>> queue =
            new DelayQueue<ItemVo<String>>();//存放已经完成的任务[并且等待过期]
    //单例模式
    private CheckJobProcesser(){}
    private static class JobPoolHolder{
        public static CheckJobProcesser processer = new CheckJobProcesser();
    }
    public static CheckJobProcesser getInstance(){
        return CheckJobProcesser.JobPoolHolder.processer;
    }
    private static class FetchJob implements Runnable{
        @Override
        public void run() {
            while (true){
                try {
                    ItemVo<String> take = queue.take();
                    String date = take.getDate();//拿到已经过期的任务
                    PendingJobPool.getMap().remove(date);
                    System.out.println(date + " is remove from map!!!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void putJob(String jobName,long expireTime){
        ItemVo<String> stringItemVo = new ItemVo<String>(expireTime,jobName);
        queue.offer(stringItemVo);
        System.out.println("job [" + jobName + "] 已经放入了过期检查缓存: " + expireTime);
    }
    static {
        Thread thread = new Thread(new FetchJob());
        thread.setDaemon(true);
        thread.start();
        System.out.println("开启任务过期，检查守护线程");
    }
}
class ItemVo<T> implements Delayed {

    private long activeTime;//到期时间  过期时长
    private T date;

    public ItemVo(long activeTime, T date) {
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
