package cn.tedu.anhuicsmall.product.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class SpuMapperTests {

    @Autowired
    private SpuMapper spuMapper;

    @Test
    public void spuIndexList(){
        spuMapper.selectIndexSpu().forEach(System.out::println);
    }
}
