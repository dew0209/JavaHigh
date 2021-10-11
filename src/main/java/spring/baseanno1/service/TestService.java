package spring.baseanno1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import spring.baseanno1.dao.TestDao;

import javax.annotation.Resource;

@Service
public class TestService {

    @Qualifier("testDao2")//利用这个在多个类型一样的bean的时候，去按名字找
    @Autowired(required = false)
    //@Resource()//required = true
    private TestDao testDao;

    public void testDao(){
        System.out.println(testDao);
    }
}
