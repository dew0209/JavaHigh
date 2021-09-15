package log.mybatis_demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class LogFactory {
    private static Constructor<? extends Log> logConstructor;//第三方日志实现类的构造器
    static {
        tryImplementation(LogFactory::useJdkLogging);
        tryImplementation(LogFactory::useNoLogging);
    }
    public static synchronized void useJdkLogging(){
        setImplementation(log.mybatis_demo.jdk.Jdk14LoggingImpl.class);//提供日志的适配类
    }
    public static synchronized void useNoLogging(){
        setImplementation(log.mybatis_demo.nologging.NoLoggingImpl.class);//提供日志的适配类
    }

    public static void tryImplementation(Runnable runnable){
        if (logConstructor == null){
            runnable.run();
        }
    }
    //传入适配器类。用适配器类完成对第三方日志框架的集成，注意，这里并不是多线的加载方式，至于为什么需要将方法加上synchronized关键字，暂时没有看到哪里需要同步。
    public static void setImplementation(Class<? extends Log> implClass){
        try {
            Constructor<? extends Log> candidate = implClass.getConstructor(String.class);
            Log log = candidate.newInstance(LogFactory.class.getName());
            if (log != null){
                System.out.println("加载日志成：" + implClass + "adapter");
            }
            logConstructor = candidate;
        } catch (Throwable e) {
            //ignore
            System.out.println("添加日志组件失败");
        }
    }
    public static Log getLog(String s){
        try {
            return logConstructor.newInstance(s);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
