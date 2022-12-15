package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.OrderAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

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
}
