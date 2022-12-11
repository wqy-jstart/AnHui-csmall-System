package cn.tedu.anhuicsmall.product;

import cn.tedu.anhuicsmall.product.pojo.entity.Spu;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

@SpringBootTest
public class ReflectionTest {

    @Test
    public void reflectionTest() {
        Spu spu = new Spu();

        Field id = null;
        try {
            id = spu.getClass().getDeclaredField("id");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        System.out.println(id);

    }
}
