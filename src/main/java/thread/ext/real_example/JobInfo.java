package thread.ext.real_example;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class JobInfo<R> {
    private final String jobName;
    private final int jobLength;
    private final ITaskProcesser<?,?> taskProcesser;
    private AtomicInteger successCount;
    private AtomicInteger taskProcesserCount;
    private LinkedBlockingDeque<TaskResult<R>> taskDetailQueue;//拿结果的时候从头拿，放从尾巴放
    private final long expireTime;

    public JobInfo(String jobName, int jobLength, ITaskProcesser<?, ?> taskProcesser, long expireTime) {
        this.jobName = jobName;
        this.jobLength = jobLength;
        this.taskProcesser = taskProcesser;
        this.successCount = new AtomicInteger(0);
        this.taskProcesserCount = new AtomicInteger(0);
        this.taskDetailQueue = new LinkedBlockingDeque<>(jobLength);
        this.expireTime = expireTime;
    }

    public ITaskProcesser<?, ?> getTaskProcesser() {
        return taskProcesser;
    }

    public int getFailCount(){
        return taskProcesserCount.get() - successCount.get();
    }
    public String getTotalProcesser(){
        return "success [" + successCount.get() +"] /Current[" + taskProcesserCount.get() + "] total [" + jobLength + "]";
    }
    public int getSuccessCount() {
        return successCount.get();
    }

    public int getTaskProcesserCount() {
        return taskProcesserCount.get();
    }

    public List<TaskResult<R>> getTaskDetail(){
        List<TaskResult<R>> taskList = new LinkedList<>();
        TaskResult<R> taskResult;
        while ((taskResult = taskDetailQueue.pollFirst()) != null){
            taskList.add(taskResult);
        }
        return taskList;
    }

    //保证最终一致性即可
    public void addTaskResult(TaskResult<R> result,CheckJobProcesser checkJobProcesser){
        if(TaskResultType.Success.equals(result.getResultType())){
            successCount.incrementAndGet();
        }
        taskDetailQueue.addLast(result);
        taskProcesserCount.incrementAndGet();
        if (taskProcesserCount.get() == jobLength)checkJobProcesser.putJob(jobName,expireTime);
    }
}

