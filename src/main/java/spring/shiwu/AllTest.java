package spring.shiwu;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.shiwu.confg.Confg;
import spring.shiwu.service.OrderService;

public class AllTest {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        OrderService service = (OrderService)app.getBean("orderService");
        service.addOrder();

    }
}
