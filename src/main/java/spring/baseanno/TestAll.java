package spring.baseanno;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.baseanno.bean.Mokey;
import spring.baseanno.confg.Confg;
import spring.quickstart.pojo.Person;

public class TestAll {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        String[] names = app.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name + "   " + app.getBean(name));
        }
    }

    //证明单实例
    @Test
    public void testo2(){
        //从容器中取两次，看是否为同一个bean,结论：是同一个对象
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        Person p = (Person)app.getBean("person");
        Person p1 = (Person)app.getBean("person");
        System.out.println(p1 == p);
    }
    //使用多实例
    @Test
    public void test03(){
        //从容器中取两次，看是否为一个bean，结论：不是同一个对象
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        Person p = (Person)app.getBean("person1");
        Person p1 = (Person)app.getBean("person1");
        System.out.println(p1 == p);
    }
    //lazy测试
    @Test
    public void test04(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        System.out.println("IOC容器创建完成");
        Object bean = app.getBean("person2");
    }

    /**
     * 运行操作系统的时候，如果windows--->lison
     *                     linux--->james
     * @Conditional
     *
     *
     * FactoryBean：把Java实例通过FactoryBean注入到容器中
     * BeanFactory：从我们的容器中获取实例化后的bean
     */
    @Test
    public void test05(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        System.out.println("IOC容器创建完成");
    }

    /**
     * @Import
     */
    @Test
    public void test06(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        System.out.println("IOC容器创建完成");
        String[] names = app.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name + "  " + app.getBean(name));
        }
        System.out.println("======");
        Mokey bean1 = (Mokey)app.getBean("myFactoryBean");
        Mokey bean2 = (Mokey)app.getBean("myFactoryBean");

        System.out.println(bean1 == bean2);
        Object bean = app.getBean("&myFactoryBean");
        System.out.println(bean);//spring.baseanno.FactoryBean.MyFactoryBean@2d7275fc  加了&就会直接获取bean工厂对象
    }

    /**
     * bean的生命周期
     */
    @Test
    public void test07(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        System.out.println("IOC容器创建完成");
//        System.out.println("华丽的分割线");
//        Object bike = app.getBean("bike");
//        System.out.println(bike);
//        app.close();
//        app.getBean("train");
//        app.close();
//        app.getBean("jeep");
//
//        app.close();
        app.getBean("bike");
    }

    @Test
    public void test08(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        System.out.println("IOC容器创建完成");
    }
}
