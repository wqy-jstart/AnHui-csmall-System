package cn.tedu.anhuicsmall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 这是MyBatis-Plus的配置类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Configuration
@MapperScan("cn.tedu.anhuicsmall.product.mapper")
@EnableTransactionManagement// 自动管理事务
public class MyBatisPlusConfiguration {

    public MyBatisPlusConfiguration(){
        log.debug("创建配置类对象:MyBatisPlusConfiguration");
    }

    // MyBatis-Plus自带的分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
