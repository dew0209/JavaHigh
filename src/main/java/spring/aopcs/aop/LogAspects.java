package spring.aopcs.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * 日志切面类的方法需要动态感知到div方法运行
 *  通知方法：
 *      前置通知：logStart():在我们执行div()除法之前运行 @Before
 *      后置通知：logEnd():在目标方法div()运行结束之后，不管有没有异常 @After
 *      返回通知：logReturn():在我们的目标方法div()正常返回值后运行 @AfterReturn
 *      异常通知：longException()：在我们的目标方法div()出现异常后运行 @AfterThrowing
 *      环绕通知：动态代理，需要手动执行joinPoint.procced() @Round
 */
@Aspect
public class LogAspects {

    @Pointcut("execution(public int spring.aopcs.aop.Calculator.div(int,int))")
    public void pointCut(){}


    //前置通知
    /*
    可以使用通配符
     */
    //JoinPoint可以获取一些有关方法的信息，不能使用ProceedingJoinPoint
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        System.out.println("除法运行...参数列表是：{}");
        Object[] args = joinPoint.getArgs();
        System.out.println(Arrays.toString(args));
        System.out.println("111");
    }
    //后置通知
    @After("pointCut()")
    public void logEnd(){
        System.out.println("除法结束...参数列表是：{}");
    }
    //返回通知
    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(int result){
        System.out.println("除法正常返回...参数列表是：{}" + result);
    }
    //异常通知
    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void logException(Exception exception){
        System.out.println("除法异常...参数列表是：{}+++++++++++++++++++");
        System.out.println(exception);
    }
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        System.out.println("环绕之前");
        Object ret = null;
        try {

            ret = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("环绕之后");
        return ret;
    }
}
