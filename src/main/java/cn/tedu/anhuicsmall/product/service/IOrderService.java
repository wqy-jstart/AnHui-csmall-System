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
     * 根据id查询未发货的数量
     * @param userId 用户id
     * @return 返回数量
     */
    Integer selectCountToNoDib(Long userId);

    /**
     * 根据id查询已发货的数量
     * @param userId 用户id
     * @return 返回数量
     */
    Integer selectCountToDib(Long userId);

    /**
     * 根据用户id查询未发货的列表
     * @param userId 用户id
     * @return 返回列表
     */
    List<OrderListVO> selectByUserIdToNotDistribute(Long userId);

    /**
     * 根据用户id查询已发货的列表
     * @param userId 用户id
     * @return 返回列表
     */
    List<OrderListVO> selectByUserIdToDistribute(Long userId);

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
     * 根据用户id和spuId查询未发货的商品订单信息
     * @param userId 用户id
     * @param spuId spuId
     * @return 返回实体类
     */
    OrderListVO selectByIdNoDib(Long userId,Long spuId);

    /**
     * 根据用户id和spuId查询已发货的商品订单信息
     * @param userId 用户id
     * @param spuId spuId
     * @return 返回实体类
     */
    OrderListVO selectById(Long userId,Long spuId);

    /**
     * 处理退货的业务
     * @param orderAddNewDTO 退货信息
     */
    void handleBackProduct(OrderAddNewDTO orderAddNewDTO);

    /**
     * 查询已发货订单列表的功能
     * @return 返回列表
     */
    List<OrderListVO> selectOrderListToDistribute();

    /**
     * 查询已退货订单列表的功能
     * @return 返回列表
     */
    List<OrderListVO> selectOrderListToBack();

    /**
     * 根据用户id查询已退货订单列表的功能
     * @return 返回列表
     */
    List<OrderListVO> selectOrderListToBackById(Long userId);

    /**
     * 根据用户id查询已退货订单数量的功能
     * @return 返回列表
     */
    Integer selectOrderCountToBackById(Long userId);
}
