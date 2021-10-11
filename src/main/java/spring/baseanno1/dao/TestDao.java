package spring.baseanno1.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository("testDao1")
public class TestDao {

    private String flag = "1";

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "TestDao{" +
                "flag='" + flag + '\'' +
                '}';
    }
}
