package cn.tedu.anhuicsmall.product.repo;

import cn.tedu.anhuicsmall.product.pojo.entity.Album;
import cn.tedu.anhuicsmall.product.pojo.entity.Brand;

import java.util.List;

/**
 * 用来缓存Redis中相册数据的接口类
 *
 * @Author java.@Wqy
 * @Version 0.0.1
 */
public interface IAlbumRedisRepository {

    String BRAND_ITEM_KEY_PREFIX = "album:item";// 相册->item数据

    String BRAND_LIST_KEY = "album:list";// 用来存放相册中的list列表的key

    String BRAND_ITEM_KEYS_KEY = "album:item-keys";// 用来标记相册中item中key成员

    /**
     * 该方法用来存储一条相册数据,不做返回
     * @param album 向redis存储的相册详情类
     */
    void save(Album album);

    /**
     * 该方法用来存储多条相册数据,空返回
     * @param albums 向redis中存储的相册List集合
     */
    void save(List<Album> albums);

    /**
     * 删除Redis中的所有数据
     * @return 返回删除的数量
     */
    Long deleteAll();

    /**
     * 根据key向redis中取出对应的item数据
     * @param id 相册id
     * @return 返回相册详情类
     */
    Album get(Long id);

    /**
     * 该方法用来取出所有的相册列表
     * @return 返回相册列表的List集合
     */
    List<Album> list();

    /**
     * 该方法用来按指定下标返回相册的任意长度列表集合
     * @param start 起始下标
     * @param end   末尾下标
     * @return 返回列表List集合
     */
    List<Album> list(long start,long end);
}
