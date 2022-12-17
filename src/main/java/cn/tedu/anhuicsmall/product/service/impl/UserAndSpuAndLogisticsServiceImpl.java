package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.*;
import cn.tedu.anhuicsmall.product.pojo.dto.UserAndSpuAndLogisticsAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.*;
import cn.tedu.anhuicsmall.product.service.IUserAndSpuAndLogisticsService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户和spu和物流的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class UserAndSpuAndLogisticsServiceImpl extends ServiceImpl<UserAndSpuAndLogisticsMapper, UserAndSpuAndLogistics> implements IUserAndSpuAndLogisticsService {

    public UserAndSpuAndLogisticsServiceImpl() {
        log.debug("创建业务层接口实现类:UserAndSpuAndLogisticsServiceImpl");
    }

    // 注入用户spu和物流关联的持久层接口
    @Autowired
    private UserAndSpuAndLogisticsMapper uslMapper;

    // 注入用户的持久层接口
    @Autowired
    private UserMapper userMapper;

    // 注入商品Spu的持久层接口
    @Autowired
    private SpuMapper spuMapper;

    // 注入物流的持久层接口
    @Autowired
    private LogisticsMapper logisticsMapper;

    // 注入订单的持久层接口
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理发货的业务
     *
     * @param uslDTO 信息
     */
    @Override
    public void insert(UserAndSpuAndLogisticsAddNewDTO uslDTO) {
        log.debug("开始处理添加关联表信息的业务,参数:{}", uslDTO);
        User queryUser = userMapper.selectById(uslDTO.getUserId());
        if (queryUser == null) {
            String message = "添加失败,该用户不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        Spu querySpu = spuMapper.selectById(uslDTO.getSpuId());
        if (querySpu == null) {
            String message = "添加失败,该Spu不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        Logistics queryLogistics = logisticsMapper.selectById(uslDTO.getLogisticsId());
        if (queryLogistics == null) {
            String message = "添加失败,该物流不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        log.debug("即将向关联表中插入信息...");
        UserAndSpuAndLogistics usl = new UserAndSpuAndLogistics();
        BeanUtils.copyProperties(uslDTO, usl);
        usl.setNote(uslDTO.getNote());
        log.debug("开始向关联表中插入信息...");
        int rows = uslMapper.insert(usl);
        if (rows > 1) {
            String message = "添加失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }

        // 修改订单表中的已发货状态
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", usl.getUserId());
        wrapper.eq("spu_id", usl.getSpuId());
        Order order = new Order();
        order.setLogisticsId(usl.getLogisticsId());
        order.setIsDistribute(1);
        int rowsToOrder = orderMapper.update(order, wrapper);
        if (rowsToOrder > 1) {
            String message = "修改失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    /**
     * 确认退货的请求
     *
     * @param userId 用户id
     * @param spuId  spuId
     */
    @Override
    public void deleteToBack(Long userId, Long spuId) {
        log.debug("开始处理根据用户id[{}]和SpuId[{}]删除订单功能", userId, spuId);
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

        QueryWrapper<Order> wrapperToOrder = new QueryWrapper<>();
        wrapperToOrder.eq("user_id", userId);
        wrapperToOrder.eq("spu_id", spuId);
        int delete = orderMapper.delete(wrapperToOrder);
        if (delete > 1) {
            String message = "操作失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }

        QueryWrapper<UserAndSpuAndLogistics> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("spu_id", spuId);
        int rows = uslMapper.delete(wrapper);

        if (rows > 1) {
            String message = "操作失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }
}
