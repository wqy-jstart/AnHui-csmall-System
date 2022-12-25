package cn.tedu.anhuicsmall.product.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;

/**
 * Redis的配置类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Configuration
public class RedisConfiguration {

    public RedisConfiguration(){
        log.debug("创建配置类对象:RedisConfiguration");
    }

    @Bean // 将配置后的方法丢到Spring容器中进行管理,便于调用
    public RedisTemplate<String, Serializable> redisTemplate(
            RedisConnectionFactory redisConnectionFactory
    ){ // 配置Redis连接工厂
        RedisTemplate<String,Serializable> redisTemplate = new RedisTemplate<>();// 创建Redis模板对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);// 设置Redis连接

        redisTemplate.setKeySerializer(RedisSerializer.string());// 创建String序列化器,序列化String类型的Key对象
        redisTemplate.setValueSerializer(RedisSerializer.json());// 创建JSON序列化器,序列化value的JSON数据
        return redisTemplate; // 最终返回
    }
}
