package spring.aopcs;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.aopcs.aop.Calculator;
import spring.aopcs.confg.Confg;

public class AllTest {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        Calculator calculator = (Calculator)app.getBean("calculator");
        int div = calculator.div(10,5);
        System.out.println(div);
    }
}
