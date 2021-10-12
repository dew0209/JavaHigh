package spring.aopcs.confg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import spring.aopcs.aop.Calculator;
import spring.aopcs.aop.LogAspects;

@Configuration
@EnableAspectJAutoProxy
public class Confg {
    @Bean("calculator")
    public Calculator calculator(){
        return new Calculator();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
