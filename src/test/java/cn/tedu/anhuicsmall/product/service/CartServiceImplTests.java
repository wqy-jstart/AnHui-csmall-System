package cn.tedu.anhuicsmall.product.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class CartServiceImplTests {

    @Autowired
    private ICartService cartService;

    @Test
    public void cartList(){
        cartService.selectCartListByUserId(1L).forEach(System.out::println);
    }
}
