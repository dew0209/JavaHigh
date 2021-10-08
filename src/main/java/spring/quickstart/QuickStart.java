package spring.quickstart;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.quickstart.pojo.Person;

/**
 * 使用xml
 */
public class QuickStart {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person per = (Person)applicationContext.getBean("per");
        System.out.println(per);
    }
}
