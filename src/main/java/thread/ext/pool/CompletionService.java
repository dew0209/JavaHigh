package thread.ext.pool;


import java.util.Random;
import java.util.concurrent.Callable;

public class CompletionService implements Callable<Integer> {
    private String name;
    public CompletionService(String name){
        this.name = name;
    }

    @Override
    public Integer call() {
        int SleepTime = new Random().nextInt(1000);
        try {
            Thread.sleep(SleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return SleepTime;
    }
}


