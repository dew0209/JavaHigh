package spring.baseanno;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.baseanno.confg.Confg;

public class TestAll {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        String[] names = app.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name + "   " + app.getBean(name));
        }
    }

}
