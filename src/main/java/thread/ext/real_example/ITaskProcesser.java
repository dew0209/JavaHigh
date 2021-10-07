package thread.ext.real_example;

public interface ITaskProcesser<T,R> {
    public TaskResult<R> taskExecute(T date);
}
