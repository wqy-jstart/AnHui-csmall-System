package cn.tedu.anhuicsmall.product.mapper;

import cn.tedu.anhuicsmall.product.pojo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert(){
        User user = new User();
        user.setUsername("admin");
        user.setNickname("管理员");
        user.setPassword("123456");
        user.setGender("男");
        user.setAge(19);
        user.setAvatar("无");
        user.setEmail("无");
        int rows = userMapper.insert(user);
        log.debug("添加成功!影响的行数为:{}",rows);
    }
}
