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
     * 查询未发货订单列表的功能
     * @return 返回列表
     */
    List<OrderListVO> selectOrderListToNotDistribute();

    /**
     * 根据用户id和spuId查询未发货的商品订单信息
     * @param userId 用户id
     * @param spuId spuId
     * @return 返回实体类
     */
    OrderListVO selectById(Long userId,Long spuId);

    /**
     * 查询已发货订单列表的功能
     * @return 返回列表
     */
    List<OrderListVO> selectOrderListToDistribute();
}
