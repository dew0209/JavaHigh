package thread.ext.real_example;

public class TaskResult<R> {
    private final TaskResultType resultType;
    private final R returnType;
    private final String reason;

    public TaskResult(TaskResultType resultType, R returnType, String reason) {
        this.resultType = resultType;
        this.returnType = returnType;
        this.reason = reason;
    }
    public TaskResult(TaskResultType resultType, R returnType) {
        this.resultType = resultType;
        this.returnType = returnType;
        this.reason = "Success";
    }

    public TaskResultType getResultType() {
        return resultType;
    }

    public R getReturnType() {
        return returnType;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "resultType=" + resultType +
                ", returnType=" + returnType +
                ", reason='" + reason + '\'' +
                '}';
    }
}
