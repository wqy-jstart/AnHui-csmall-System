package cn.tedu.anhuicsmall.product.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class OrderMapperTests {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void selectOrderListToDistribute(){
        orderMapper.selectOrderListToDistribute().forEach(System.out::println);
    }

    @Test
    public void selectOrderListToNotDistribute(){
        orderMapper.selectOrderListToNotDistribute().forEach(System.out::println);
    }
}
