package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.AddressMapper;
import cn.tedu.anhuicsmall.product.mapper.OrderMapper;
import cn.tedu.anhuicsmall.product.mapper.SpuMapper;
import cn.tedu.anhuicsmall.product.mapper.UserMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.OrderAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Address;
import cn.tedu.anhuicsmall.product.pojo.entity.Order;
import cn.tedu.anhuicsmall.product.pojo.entity.Spu;
import cn.tedu.anhuicsmall.product.pojo.entity.User;
import cn.tedu.anhuicsmall.product.pojo.vo.OrderListVO;
import cn.tedu.anhuicsmall.product.service.IOrderService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    public OrderServiceImpl() {
        log.debug("创建业务层实现类:OrderServiceImpl");
    }

    // 注入订单的持久层接口
    @Autowired
    private OrderMapper orderMapper;

    // 注入用户的持久层接口
    @Autowired
    private UserMapper userMapper;

    // 注入Spu的持久层接口
    @Autowired
    private SpuMapper spuMapper;

    // 注入收货信息的持久层接口
    @Autowired
    private AddressMapper addressMapper;

    /**
     * 根据用户id查询未发货的订单数量
     * @param userId 用户id
     * @return 返回数量
     */
    @Override
    public Integer selectCountToNoDib(Long userId) {
        log.debug("开始处理根据用户id查询未发货的商品数量,参数:{}",userId);
        return orderMapper.selectCountToNoDib(userId);
    }

    /**
     * 根据用户id查询已发货的订单数量
     * @param userId 用户id
     * @return 返回数量
     */
    @Override
    public Integer selectCountToDib(Long userId) {
        log.debug("开始处理根据用户id查询已发货的商品数量,参数:{}",userId);
        return orderMapper.selectCountToDib(userId);
    }

    /**
     * 根据用户id查询未发货列表
     *
     * @param userId 用户id
     * @return 返回未发货列表
     */
    @Override
    public List<OrderListVO> selectByUserIdToNotDistribute(Long userId) {
        log.debug("开始处理查询用户id为{}的未发货列表", userId);
        User queryUser = userMapper.selectById(userId);
        if (queryUser == null) {
            String message = "查询失败,该用户不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return orderMapper.selectByUserIdToNotDistribute(userId);
    }

    /**
     * 根据用户id查询已发货列表
     *
     * @param userId 用户id
     * @return 返回已发货列表
     */
    @Override
    public List<OrderListVO> selectByUserIdToDistribute(Long userId) {
        log.debug("开始处理查询用户id为{}的已发货列表", userId);
        User queryUser = userMapper.selectById(userId);
        if (queryUser == null) {
            String message = "查询失败,该用户不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return orderMapper.selectByUserIdToDistribute(userId);
    }

    /**
     * 添加订单信息的业务
     *
     * @param orderAddNewDTO 添加的订单信息
     */
    @Override
    public void insert(OrderAddNewDTO orderAddNewDTO) {
        log.debug("开始处理添加订单信息的业务,参数:{}", orderAddNewDTO);
        User queryUser = userMapper.selectById(orderAddNewDTO.getUserId());
        if (queryUser == null) {
            String message = "添加订单失败,该用户不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        Spu querySpu = spuMapper.selectById(orderAddNewDTO.getSpuId());
        if (querySpu == null) {
            String message = "添加订单失败,该商品不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        Address queryAddress = addressMapper.selectById(orderAddNewDTO.getAddressId());
        if (queryAddress == null) {
            String message = "添加订单失败,该收货地址不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        log.debug("即将向数据库中添加订单信息...");
        Order order = new Order();
        BeanUtils.copyProperties(orderAddNewDTO, order);
        order.setTime(new Date());
        order.setIsPay(1);
        log.debug("开始向数据库中添加订单信息...");
        int rows = orderMapper.insert(order);
        if (rows > 1) {
            String message = "添加订单失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    /**
     * 处理查询未发货订单列表的功能
     *
     * @return 返回列表
     */
    @Override
    public List<OrderListVO> selectOrderListToNotDistribute() {
        log.debug("开始处理查询未发货订单列表的功能,无参!");
        return orderMapper.selectOrderListToNotDistribute();
    }

    /**
     * 根据用户id和SpuId查询未发货订单数据
     *
     * @param userId 用户id
     * @param spuId  spuId
     * @return 返回订单信息
     */
    @Override
    public OrderListVO selectByIdNoDib(Long userId, Long spuId) {
        log.debug("开始处理查询用户[{}]和spu[{}]的订单信息", userId, spuId);
        User queryUser = userMapper.selectById(userId);
        if (queryUser == null) {
            String message = "查询失败,该用户不存在";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        Spu querySpu = spuMapper.selectById(spuId);
        if (querySpu == null) {
            String message = "查询失败,该Spu不存在";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        return orderMapper.selectByIdNoDib(userId, spuId);
    }

    /**
     * 根据用户id和SpuId查询已发货订单数据
     *
     * @param userId 用户id
     * @param spuId  spuId
     * @return 返回订单信息
     */
    @Override
    public OrderListVO selectById(Long userId, Long spuId) {
        log.debug("开始处理查询已发货用户[{}]和spu[{}]的订单信息", userId, spuId);
        User queryUser = userMapper.selectById(userId);
        if (queryUser == null) {
            String message = "查询失败,该用户不存在";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        Spu querySpu = spuMapper.selectById(spuId);
        if (querySpu == null) {
            String message = "查询失败,该Spu不存在";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        return orderMapper.selectById(userId, spuId);
    }

    /**
     * 处理修改退货状态的业务
     * @param orderAddNewDTO 退货信息
     */
    @Override
    public void handleBackProduct(OrderAddNewDTO orderAddNewDTO) {
        log.debug("开始处理退货的业务,参数:{}",orderAddNewDTO);
        User queryUser = userMapper.selectById(orderAddNewDTO.getUserId());
        if (queryUser == null) {
            String message = "查询失败,该用户不存在";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        Spu querySpu = spuMapper.selectById(orderAddNewDTO.getSpuId());
        if (querySpu == null) {
            String message = "查询失败,该Spu不存在";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        // 修改订单状态为退货状态并接收退货信息,后端显示退货信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",orderAddNewDTO.getUserId());
        wrapper.eq("spu_id",orderAddNewDTO.getSpuId());
        Order order = new Order();
        order.setUserId(orderAddNewDTO.getUserId());
        order.setSpuId(orderAddNewDTO.getSpuId());
        order.setIsBack(1);
        order.setBackText(orderAddNewDTO.getBackText());
        int rows = orderMapper.update(order, wrapper);
        if (rows>1){
            String message = "退货失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE,message);
        }
    }

    /**
     * 处理查询已发货订单列表的功能
     *
     * @return 返回列表
     */
    @Override
    public List<OrderListVO> selectOrderListToDistribute() {
        log.debug("开始处理查询已发货订单列表的功能,无参!");
        return orderMapper.selectOrderListToDistribute();
    }

    /**
     * 查询后台已退货的订单列表
     * @return 返回列表
     */
    @Override
    public List<OrderListVO> selectOrderListToBack() {
        log.debug("开始处理已退货的订单列表,无参!");
        return orderMapper.selectOrderListToBack();
    }

    /**
     * 根据用户id查询已退货的列表信息
     * @param userId 用户id
     * @return 返回列表信息
     */
    @Override
    public List<OrderListVO> selectOrderListToBackById(Long userId) {
        User queryUser = userMapper.selectById(userId);
        if (queryUser == null) {
            String message = "查询失败,该用户不存在";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
       return orderMapper.selectOrderListToBackById(userId);
    }

    /**
     * 根据用户id查询已退货的订单数量
     * @param userId 用户id
     * @return 返回订单数量
     */
    @Override
    public Integer selectOrderCountToBackById(Long userId) {
        User queryUser = userMapper.selectById(userId);
        if (queryUser == null) {
            String message = "查询失败,该用户不存在";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return orderMapper.selectOrderCountToBackById(userId);
    }
}
