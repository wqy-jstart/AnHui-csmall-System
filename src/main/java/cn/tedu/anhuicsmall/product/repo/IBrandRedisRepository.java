package cn.tedu.anhuicsmall.product.repo;

import cn.tedu.anhuicsmall.product.pojo.entity.Brand;

import java.util.List;

/**
 * 用来缓存Redis中品牌数据的接口类
 *
 * @Author java.@Wqy
 * @Version 0.0.1
 */
public interface IBrandRedisRepository {

    String BRAND_ITEM_KEY_PREFIX = "brand:item";// 品牌->item数据

    String BRAND_LIST_KEY = "brand:list";// 用来存放品牌中的list列表的key

    String BRAND_ITEM_KEYS_KEY = "brand:item-keys";// 用来标记品牌中item中key成员

    /**
     * 该方法用来存储一条品牌数据,不做返回
     * @param brand 向redis存储的品牌详情类
     */
    void save(Brand brand);

    /**
     * 该方法用来存储多条品牌数据,空返回
     * @param brands 向redis中存储的品牌List集合
     */
    void save(List<Brand> brands);

    /**
     * 删除Redis中的所有数据
     * @return 返回删除的数量
     */
    Long deleteAll();

    /**
     * 根据key向redis中取出对应的item数据
     * @param id 品牌id
     * @return 返回品牌详情类
     */
    Brand get(Long id);

    /**
     * 该方法用来取出所有的品牌列表
     * @return 返回品牌列表的List集合
     */
    List<Brand> list();

    /**
     * 该方法用来按指定下标返回品牌的任意长度列表集合
     * @param start 起始下标
     * @param end   末尾下标
     * @return 返回列表List集合
     */
    List<Brand> list(long start,long end);
}
