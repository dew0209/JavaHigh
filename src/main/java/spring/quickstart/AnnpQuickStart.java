package spring.quickstart;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.quickstart.pojo.Person;

/**
 * 配置类=配置文件
 */
@Configuration
public class AnnpQuickStart {

    //给容器注册一个bean，类型为返回值类型
    @Bean
    public Person person(){
        return new Person("james",23);
    }
}
/**
 * 使用注解
 */
class Test{
    public static void main(String[] args) {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(AnnpQuickStart.class);
        Person person = (Person)app.getBean("person");
        System.out.println(person);
    }
}
