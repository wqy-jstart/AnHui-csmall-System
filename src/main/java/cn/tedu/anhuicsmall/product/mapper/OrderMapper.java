package cn.tedu.anhuicsmall.product.mapper;

import cn.tedu.anhuicsmall.product.pojo.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 订单信息的持久层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {
}
