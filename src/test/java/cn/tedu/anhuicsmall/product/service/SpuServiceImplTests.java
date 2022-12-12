package cn.tedu.anhuicsmall.product.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class SpuServiceImplTests {

    @Autowired
    private ISpuService spuService;

    @Test
    public void selectSortByTitle(){
        spuService.selectSortByTitle().forEach(System.out::println);
    }

    @Test
    public void selectByCategoryId(){
        spuService.selectByCategoryId(3L).forEach(System.out::println);
    }
}
