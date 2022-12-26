package cn.tedu.anhuicsmall.product.preload;

import cn.tedu.anhuicsmall.product.mapper.BrandMapper;
import cn.tedu.anhuicsmall.product.pojo.entity.Brand;
import cn.tedu.anhuicsmall.product.repo.IBrandRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 实现Redis缓存预热
 *
 * @Author java.@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Component // 声明一个组件类,可被Spring调用
public class CachePreload implements ApplicationRunner {

    // 注入品牌的持久层接口
    @Autowired
    private BrandMapper brandMapper;

    // 注入品牌的Redis缓存接口
    @Autowired
    private IBrandRedisRepository brandRedisRepository;

    // ApplicationRunner中的run()方法会在项目启动成功之后自动执行
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("CacheSchedule.run()");

        log.debug("准备删除Redis缓存中的品牌数据...");
        brandRedisRepository.deleteAll();
        log.debug("删除Redis缓存中的品牌数据,完成!");

        log.debug("准备从数据库中读取品牌列表...");
        List<Brand> brands = brandMapper.selectList(null);
        log.debug("从数据库中读取品牌列表,完成!");

        log.debug("准备将品牌列表写入到Redis缓存...");
        brandRedisRepository.save(brands);
        log.debug("将品牌列表写入到Redis缓存,完成!");
    }
}
