package thread.ext.real_example;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class PendingJobPool {
    private static CheckJobProcesser checkJob = CheckJobProcesser.getInstance();
    //线程池大小的保守估计
    private static final int THREAD_COUNTS = Runtime.getRuntime().availableProcessors();
    //任务队列
    private static BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(5000);

    //线程池，固定大小
    private static ExecutorService taskExecutor = new ThreadPoolExecutor(THREAD_COUNTS,
            THREAD_COUNTS,60, TimeUnit.SECONDS,taskQueue);
    //job的存放容器
    private static ConcurrentHashMap<String,JobInfo<?>> jobInfoMap = new ConcurrentHashMap<>();

    //单例模式
    private PendingJobPool(){}
    private static class JobPoolHolder{
        public static PendingJobPool pool = new PendingJobPool();
    }
    public static PendingJobPool getInstance(){
        return JobPoolHolder.pool;
    }

    public static Map<String,JobInfo<?>> getMap(){
        return jobInfoMap;
    }

    private <R> JobInfo<R> getJob(String jobName){
        JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
        if(jobInfo == null)throw new RuntimeException("非法的任务");
        return jobInfo;
    }

    public <T,R> void putTask(String jobName,T t){
        JobInfo<R> jobInfo = getJob(jobName);
        PendingTask<T,R> task = new PendingTask<T,R>(jobInfo,t);
        taskExecutor.execute(task);
    }

    public <R> void registerJon(String jobName,int jobLength,ITaskProcesser<?,?> taskProcesser,long expireTime){
        JobInfo<R> jobInfo = new JobInfo<>(jobName,jobLength,taskProcesser,expireTime);
        if(jobInfoMap.putIfAbsent(jobName,jobInfo) != null){
            throw new RuntimeException("任务已经注册了");
        }
    }
    public <R> List<TaskResult<R>> getTaskDetail(String jobName){
        JobInfo<R> job = getJob(jobName);
        return job.getTaskDetail();
    }

    public <R> String getTaskProcess(String jobName){
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTotalProcesser();
    }

    private static class PendingTask<T,R> implements Runnable{
        private JobInfo<R> jobInfo;
        private T processData;

        public PendingTask(JobInfo<R> jobInfo, T processData) {
            this.jobInfo = jobInfo;
            this.processData = processData;
        }

        @Override
        public void run() {
            R r = null;
            ITaskProcesser<T, R> taskProcesser = (ITaskProcesser<T, R>) jobInfo.getTaskProcesser();
            //调用业务人员实现的具体方法
            TaskResult<R> result = null;
            try {
                result = taskProcesser.taskExecute(processData);
                //要做检查
                if (result == null) {
                    result = new TaskResult<R>(TaskResultType.Exception, r, "return is null");
                }
                if (result.getResultType() == null) {
                    if (result.getReason() == null) {
                        result = new TaskResult<R>(TaskResultType.Exception, r, "reason is null");
                    } else {
                        result = new TaskResult<R>(TaskResultType.Exception, r, "return is null but reason: " + result.getReason());
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                result = new TaskResult<R>(TaskResultType.Exception, r, e.getMessage());
            } finally {
                jobInfo.addTaskResult(result,checkJob);

            }
        }
    }

}
