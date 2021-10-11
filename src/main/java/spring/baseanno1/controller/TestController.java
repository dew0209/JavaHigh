package spring.baseanno1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import spring.baseanno1.service.TestService;

@Controller
public class TestController {
    @Autowired
    private TestService testService;
}
