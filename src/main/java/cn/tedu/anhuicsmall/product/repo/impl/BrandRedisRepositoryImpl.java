package cn.tedu.anhuicsmall.product.repo.impl;

import cn.tedu.anhuicsmall.product.pojo.entity.Brand;
import cn.tedu.anhuicsmall.product.repo.IBrandRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 处理Redis缓存中品牌缓冲的实现类
 *
 * @Author java.@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Repository
public class BrandRedisRepositoryImpl implements IBrandRedisRepository {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    public BrandRedisRepositoryImpl(){
        log.debug("创建处理品牌缓存的数据访问实现类对象:BrandRedisRepositoryImpl");
    }

    /**
     * 向Redis中存入品牌详情信息
     * @param brand 向redis存储的品牌详情类
     */
    @Override
    public void save(Brand brand) {
        String key = BRAND_ITEM_KEY_PREFIX+brand.getId();// 定义一个唯一的key值
        log.debug("向Set集合中存入此处查找的Key值"); // 因为Redis中的key值是不允许重复的,在添加数据时,将key单独进行记录
        redisTemplate.opsForSet().add(BRAND_ITEM_KEYS_KEY,key);
        redisTemplate.opsForValue().set(key,brand); // 将品牌存到key中
        log.debug("向缓存中存入品牌详情数据成功!");
    }

    /**
     * 实现向Redis中存入列表数据的功能
     * @param brands 向redis中存储的品牌List集合
     */
    @Override
    public void save(List<Brand> brands) {
        String key = BRAND_LIST_KEY; // 定义一个用来存储品牌列表数据的key
        ListOperations<String, Serializable> ops = redisTemplate.opsForList(); // 基于redis模板创建一个List集合
        for (Brand brand : brands) {
            ops.rightPush(key,brand);// 遍历要存入的品牌列表,放到Redis的指定key对应的集合中
        }
    }

    /**
     * 清空Redis缓存中所有关于品牌的记录
     * @return 返回数据
     */
    @Override
    public Long deleteAll() {
        Set<Serializable> members = redisTemplate.opsForSet().members(BRAND_ITEM_KEYS_KEY);// 获取到key集合中的每一个key
        Set<String> keys = new HashSet<>();// 创建一个Set集合
        for (Serializable member : members) { // 将拿到的key进行遍历,放到Set集合中,例:brand:item1
            keys.add((String) member);
        }
        keys.add(BRAND_LIST_KEY);// 将Redis中的品牌列表也放进Set集合,brand:list
        keys.add(BRAND_ITEM_KEYS_KEY); // 将存放key列表的key也放入Set集合,brand:item-keys
        return redisTemplate.delete(keys); // 调用delete()传入set集合,将结果进行返回
    }

    /**
     * 根据id获取缓存中的一条数据
     * @param id 品牌id
     * @return 返回数据
     */
    @Override
    public Brand get(Long id) {
        Serializable serializable = redisTemplate.opsForValue().get(BRAND_ITEM_KEY_PREFIX + id);// 利用id拿到缓存中的key对应的value
        Brand brand = null;
        if (serializable!= null){ // 判断拿到的value是否为空
            if (serializable instanceof Brand){ // 判断拿到的value与即将要存入的对象是否存在可转换关系
                brand = (Brand) serializable; // 转换
            }
        }
        return brand; // 返回
    }

    /**
     * 向Redis缓存中查询所有品牌列表数据
     * @return 返回数据
     */
    @Override
    public List<Brand> list() {
       long start = 0;
       long end = -1;
       return list(start,end);
    }

    /**
     * 向Redis缓存中查询指定范围的品牌列表数据
     * @param start 起始下标
     * @param end   末尾下标
     * @return 返回数据
     */
    @Override
    public List<Brand> list(long start, long end) {
        String key = BRAND_LIST_KEY; // 拿到品牌列表的Key值
        ListOperations<String, Serializable> ops = redisTemplate.opsForList(); // 创建一个存放集合的Redis模板
        List<Serializable> list = ops.range(key, start, end); // 调用range()方法,转入List的Key和列表下标,返回对应长度的列表
        List<Brand> brands = new ArrayList<>(); // 因为集合中泛型不同,所以创建一个List集合
        for (Serializable item : list) { // 遍历返回的List数据
            brands.add((Brand) item); // add到自己创建的ArrayList集合中
        }
        return brands; // 最终返回
    }
}
