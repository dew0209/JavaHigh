package spring.quickstart;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.quickstart.pojo.Person;

/**
 * 配置类=配置文件
 */
//告诉spring 这是一个配置类
@Configuration
public class AnnpQuickStart {

    //给容器注册一个bean，类型为返回值类型
    //不写abc，缺省和方法名一致
    @Bean("abc")
    public Person person(){
        return new Person("james",23);
    }
}
/**
 * 使用注解
 *
 * java bean 实例
 * map对象
 */
class Test{
    public static void main(String[] args) {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(AnnpQuickStart.class);
        Person person = (Person)app.getBean("abc");
        System.out.println(person);
        //找bean的id，和方法名字一致
        String[] namesForType = app.getBeanNamesForType(Person.class);
        for (String s : namesForType) {
            System.out.println(s);
        }
    }
}
