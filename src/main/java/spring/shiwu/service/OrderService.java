package spring.shiwu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.shiwu.dao.OrderDao;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public void addOrder(){
        orderDao.insert();
    }
}
