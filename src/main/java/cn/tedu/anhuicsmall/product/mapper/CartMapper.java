package cn.tedu.anhuicsmall.product.mapper;

import cn.tedu.anhuicsmall.product.pojo.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 购物车的持久层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Repository
public interface CartMapper extends BaseMapper<Cart> {
}
