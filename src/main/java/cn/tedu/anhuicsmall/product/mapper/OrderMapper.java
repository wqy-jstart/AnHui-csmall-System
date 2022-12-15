package cn.tedu.anhuicsmall.product.mapper;

import cn.tedu.anhuicsmall.product.pojo.entity.Order;
import cn.tedu.anhuicsmall.product.pojo.vo.OrderListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单信息的持久层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 查询未发货订单列表的功能
     * @return 返回列表
     */
    List<OrderListVO> selectOrderListToNotDistribute();

    /**
     * 查询已发货订单列表的功能
     * @return 返回列表
     */
    List<OrderListVO> selectOrderListToDistribute();
}
