package cn.tedu.anhuicsmall.product.mapper;

import cn.tedu.anhuicsmall.product.pojo.entity.Attribute;
import cn.tedu.anhuicsmall.product.pojo.entity.Picture;
import cn.tedu.anhuicsmall.product.pojo.entity.Spu;
import cn.tedu.anhuicsmall.product.pojo.vo.ProductDetailVO;
import cn.tedu.anhuicsmall.product.pojo.vo.SpuIndexListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SPU商品详情的持久层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Repository
public interface SpuMapper extends BaseMapper<Spu> {

    /**
     * 查询主页列表数据
     * @return 返回List集合
     */
    List<SpuIndexListVO> selectIndexSpu();

    /**
     * 查询根据销量降序排列的Spu列表
     * @return 返回列表信息
     */
    List<SpuIndexListVO> selectSortByTitle();

    /**
     * 查询根据分类id查询主页列表信息的功能
     * @param categoryId 分类id
     * @return 返回分类信息
     */
    List<SpuIndexListVO> selectByCategoryId(Long categoryId);

    /**
     * 模糊查询主页商品列表
     * @param wd 模糊字段
     * @return 返回列表信息
     */
    List<SpuIndexListVO> selectByWd(String wd);

    /**
     * 根据SpuId查询商品详情信息
     * @param spuId 商品id
     * @return 返回实体类
     */
    ProductDetailVO selectDetailById(Long spuId);

    /**
     * 根据spuId查询属性列表
     * @param spuId spuId
     * @return 返回属性列表
     */
    List<Attribute> selectBySpuId(Long spuId);

    /**
     * 根据spuId查询图片url
     * @param spuId spuId
     * @return 返回url列表
     */
    List<Picture> selectToPictureUrls(Long spuId);
}
