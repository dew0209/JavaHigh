package spring.baseanno1;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import spring.baseanno1.bean.Bird;
import spring.baseanno1.bean.Moon;
import spring.baseanno1.bean.Sun;
import spring.baseanno1.confg.Confg;
import spring.baseanno1.dao.TestDao;
import spring.baseanno1.service.TestService;

public class TestAll {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        System.out.println("容器已经创建完成");
        Bird bird = (Bird)app.getBean("bird");
        System.out.println(bird);
        System.out.println("分割线");
        String[] names = app.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name + "---" + app.getBean(name));
        }
        ConfigurableEnvironment en = app.getEnvironment();
        System.out.println("en === " + en.getProperty("bird.color"));
    }

    @Test
    public void test02(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        System.out.println("容器已经创建完成");

        TestService service = app.getBean(TestService.class);
        System.out.println("===");

        service.testDao();
        TestDao bean = (TestDao) app.getBean("testDao2");
        System.out.println(bean);

    }

    @Test
    public void test03(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Confg.class);
        Moon bean = app.getBean(Moon.class);
        System.out.println(bean);
        Sun bean1 = app.getBean(Sun.class);
        System.out.println(bean1.getMoon());
        System.out.println("===");
        String[] anmes = app.getBeanDefinitionNames();
        for (String anme : anmes) {
            System.out.println(anme + " ---> " + app.getBean(anme));
        }
        app.close();
    }
}
