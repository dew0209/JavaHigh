package thread.ext.real_example;


import java.util.List;
import java.util.Random;

public class TestExample implements ITaskProcesser<Integer,Integer>{

    @Override
    public TaskResult<Integer> taskExecute(Integer date) {
        Random r = new Random();
        int flag = r.nextInt(500);
        try {
            Thread.sleep(flag);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(flag <= 300){
            Integer retVal = date.intValue() + flag;
            return new TaskResult<Integer>(TaskResultType.Success,retVal);
        }else if(flag >= 301 && flag <= 400){
            return new TaskResult<Integer>(TaskResultType.Failure,-1,"Failure");
        }else {
            try {
                throw new RuntimeException("发生异常了");
            }catch (Exception e) {
                return new TaskResult<Integer>(TaskResultType.Exception, -1, e.getMessage());
            }
        }
    }
}


class AppTest{
    private final static String JOB_NAME = "计算数值";
    private final static int JOB_LENGTH = 1000;
    private static class QueryResult implements Runnable{
        private PendingJobPool pool;

        public QueryResult(PendingJobPool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            int i = 0;//查询次数
            while (i < 200){
                List<TaskResult<String>> taskDetail = pool.getTaskDetail(JOB_NAME);
                if(!taskDetail.isEmpty()){
                    System.out.println(pool.getTaskProcess(JOB_NAME));
                    System.out.println(taskDetail);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    public static void main(String[] args) {
        TestExample testExample = new TestExample();
        PendingJobPool pool = PendingJobPool.getInstance();
        pool.registerJon(JOB_NAME,JOB_LENGTH,testExample,1000*5);
        Random r = new Random();
        for(int i = 0;i < JOB_LENGTH;i++){
            pool.putTask(JOB_NAME,r.nextInt(1000));
        }
        Thread t = new Thread(new QueryResult(pool));
        t.start();
    }
}