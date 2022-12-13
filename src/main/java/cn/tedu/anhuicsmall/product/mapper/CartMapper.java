package cn.tedu.anhuicsmall.product.mapper;

import cn.tedu.anhuicsmall.product.pojo.entity.Attribute;
import cn.tedu.anhuicsmall.product.pojo.entity.Cart;
import cn.tedu.anhuicsmall.product.pojo.vo.CartListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 购物车的持久层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Repository
public interface CartMapper extends BaseMapper<Cart> {

    /**
     * 根据用户id查询商品数量
     * @return 返回数量
     */
    int selectToCount(Long userId);

    /**
     * 根据用户id查询spuId放到List集合中
     * @param userId userId
     * @return 返回spuId列表
     */
    List<Long> selectToSpuId(Long userId);

    /**
     * 根据spuId查询属性模板列表
     * @param spuId spuId
     * @return 返回列表
     */
    List<Attribute> selectToAttribute(Long spuId);

    /**
     * 根据用户id查询购物车列表
     * @param userId 用户id
     * @return 返回购物车列表
     */
    List<CartListVO> selectCartListByUserId(Long userId);
}
