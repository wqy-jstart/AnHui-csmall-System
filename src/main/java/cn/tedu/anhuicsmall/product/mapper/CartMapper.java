package cn.tedu.anhuicsmall.product.mapper;

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
     * 根据用户id查询购物车列表
     * @param userId 用户id
     * @return 返回购物车列表
     */
    List<CartListVO> selectCartListByUserId(Long userId);

    /**
     * 根据用户id查询购物车商品价格总和
     * @param userId 用户id
     * @return 返回商品价格总和
     */
    Integer selectSUMPrice(Long userId);

    /**
     * 根据用户id查询购物车数量
     * @param userId 用户id
     * @return 数量
     */
    Integer selectCount(Long userId);
}
