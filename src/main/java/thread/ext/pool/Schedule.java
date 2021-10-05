package thread.ext.pool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Schedule {

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor sched = new ScheduledThreadPoolExecutor(1);
        sched.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.HashException),1000,3000, TimeUnit.MILLISECONDS);
        sched.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.Normal),1000,3000, TimeUnit.MILLISECONDS);
    }

}
class ScheduleWorker implements Runnable{

    public final static int Normal = 0;//普通任务类型
    public final static int HashException = 1;//会抛出异常的任务
    public final static int ProcessException = 2;//抛出异常但会捕捉的任务类型

    public ScheduleWorker(int taskType) {
        this.taskType = taskType;
    }

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int taskType;
    @Override
    public void run() {
        if(taskType == HashException){
            System.out.println(format.format(new Date()) + "Exception ");
            throw new RuntimeException("HashException");
        }else if(taskType == ProcessException){
            try {
                System.out.println(format.format(new Date()) + "Exception but catch ");
                throw new RuntimeException("HashException");
            } catch (Exception e) {
                System.out.println("异常被捕捉了");
            }
        }else{
            System.out.println("normal..." + format.format(new Date()));
        }

    }
}
