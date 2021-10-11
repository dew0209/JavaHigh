package spring.baseanno1.confg;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import spring.baseanno1.dao.TestDao;
import thread.ext.aqs.conditionex.Test;


@Configuration
@ComponentScans({
        @ComponentScan(value = "spring.baseanno1")
})
@PropertySource(value = "classpath:/bird.properties")
@Component
public class Confg {

    //两个id一样的话，@Bean更占优势

    @Primary
    @Bean("testDao2")
    public TestDao testDao(){
        TestDao testDao = new TestDao();
        testDao.setFlag("2");
        return testDao;
    }

}
