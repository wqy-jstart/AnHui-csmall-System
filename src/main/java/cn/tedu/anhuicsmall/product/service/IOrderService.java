package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.OrderAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Order;
import cn.tedu.anhuicsmall.product.pojo.vo.OrderListVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单的业务层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface IOrderService extends IService<Order> {

    /**
     * 添加订单的业务
     * @param orderAddNewDTO 添加的订单信息
     */
    void insert(OrderAddNewDTO orderAddNewDTO);

    /**
     * 查询订单列表的功能
     * @return 返回列表
     */
    List<OrderListVO> selectOrderListToNotDistribute();

    /**
     * 查询已发货订单列表的功能
     * @return 返回列表
     */
    List<OrderListVO> selectOrderListToDistribute();
}
