package cn.tedu.anhuicsmall.product.preload;

import cn.tedu.anhuicsmall.product.mapper.AlbumMapper;
import cn.tedu.anhuicsmall.product.mapper.BrandMapper;
import cn.tedu.anhuicsmall.product.pojo.entity.Album;
import cn.tedu.anhuicsmall.product.pojo.entity.Brand;
import cn.tedu.anhuicsmall.product.repo.IAlbumRedisRepository;
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

    // 注入相册的持久层接口
    @Autowired
    private AlbumMapper albumMapper;

    // 注入相册的Redis缓存接口
    @Autowired
    private IAlbumRedisRepository albumRedisRepository;

    // ApplicationRunner中的run()方法会在项目启动成功之后自动执行
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("CacheSchedule.run()");

        log.debug("准备删除Redis缓存中的相册数据...");
        albumRedisRepository.deleteAll();
        log.debug("删除Redis缓存中的相册数据,完成!");

        log.debug("准备从数据库中读取相册列表...");
        List<Album> albums = albumMapper.selectList(null);
        log.debug("从数据库中读取相册列表,完成!");

        log.debug("准备将相册列表写入到Redis缓存...");
        albumRedisRepository.save(albums);
        log.debug("将相册列表写入到Redis缓存,完成!");
    }
}
