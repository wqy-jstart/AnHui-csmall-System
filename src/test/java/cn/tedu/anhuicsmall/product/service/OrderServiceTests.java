package cn.tedu.anhuicsmall.product.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class OrderServiceTests {

    @Autowired
    private IOrderService orderService;

    @Test
    public void selectToOrder(){
        orderService.selectOrderListToDistribute().forEach(System.out::println);
    }

    @Test
    public void selectToOrder1(){
        orderService.selectOrderListToNotDistribute().forEach(System.out::println);
    }

    @Test
    public void selectToOrder2(){
        orderService.selectOrderListToBack().forEach(System.out::println);
    }
}
