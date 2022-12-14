package cn.tedu.anhuicsmall.product;

import cn.tedu.anhuicsmall.product.util.BCryptEncode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class EncodeTests {

    @Test
    public void encode(){
        String num = "123456";
        String password = "$2a$10$rNVEAdyvu8IeLXo.J5/UAuuVxk3jAlp88R3P7gFSwCoJmr59DXu.y";
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        boolean matches = bc.matches(num, password);
        System.out.println(matches);
    }
}
