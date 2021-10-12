package spring.shiwu.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public class OrderDao {


    /**
     * CREATE TABLE `order` (
     *    orderid INT(11) NOT NULL AUTO_INCREMENT,
     *    ordertime DATETIME DEFAULT NULL,
     *    ordermoney DECIMAL(20,0) DEFAULT NULL,
     *    orderstatus CHAR(1) DEFAULT NULL,
     *    KEY orderid (orderid)
     * )ENGINE = INNODB AUTO_INCREMENT = 15 DEFAULT CHARSET = utf8
     */


    @Autowired
    public JdbcTemplate jdbcTemplate;
    //操作数据库
    @Transactional
    public void insert(){
        String sql = "insert into `order` (ordertime,ordermoney,orderstatus) values(?,?,?)";
        jdbcTemplate.update(sql,new Date(),20,0);
        //int a = 1 / 0;
    }
}
