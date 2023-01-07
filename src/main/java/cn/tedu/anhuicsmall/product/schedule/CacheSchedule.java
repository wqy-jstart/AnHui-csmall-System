package cn.tedu.anhuicsmall.product.schedule;

import cn.tedu.anhuicsmall.product.service.IAlbumService;
import cn.tedu.anhuicsmall.product.service.IBrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 利用计划任务来完成缓存的加载,计划重建缓存的时间
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
//@Component // 声明是一个组件类
public class CacheSchedule {

    @Autowired
    private IAlbumService albumService;

    public CacheSchedule() {
        log.debug("创建计划任务对象:CacheSchedule");
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void rebuildCache() {
        log.debug("开始执行【重建相册缓存】计划任务…………");
        albumService.rebuildCache();// 调用BrandService中重新加载Redis缓存的方法
        log.debug("本次【重建相册缓存】计划任务执行完成！");
    }
}
