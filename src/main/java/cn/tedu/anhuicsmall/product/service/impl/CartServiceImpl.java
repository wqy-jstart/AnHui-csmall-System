package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.CartMapper;
import cn.tedu.anhuicsmall.product.mapper.UserMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.CartAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Attribute;
import cn.tedu.anhuicsmall.product.pojo.entity.Cart;
import cn.tedu.anhuicsmall.product.pojo.entity.User;
import cn.tedu.anhuicsmall.product.pojo.vo.CartListVO;
import cn.tedu.anhuicsmall.product.service.ICartService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    public CartServiceImpl() {
        log.debug("创建业务层接口实现类:CartServiceImpl");
    }

    // 注入购物车持久层接口
    @Autowired
    private CartMapper cartMapper;

    // 注入用户的持久层接口
    @Autowired
    private UserMapper userMapper;

    /**
     * 处理添加购物车的业务
     *
     * @param cartAddNewDTO 添加的购物车信息
     */
    @Override
    public void insert(CartAddNewDTO cartAddNewDTO) {
        log.debug("开始处理添加购物车数据的功能,参数:{}", cartAddNewDTO);
        // 判断用户名是否存在
        User queryUser = userMapper.selectById(cartAddNewDTO.getUserId());
        if (queryUser == null) {
            String message = "添加失败,该用户数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", cartAddNewDTO.getUserId());
        wrapper.eq("spu_id", cartAddNewDTO.getSpuId());
        Cart cart1 = cartMapper.selectOne(wrapper);
        if (cart1 != null) {
            String message = "该宝贝已经在购物车中啦~";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        Cart cart = new Cart();
        BeanUtils.copyProperties(cartAddNewDTO, cart);
        int rows = cartMapper.insert(cart);
        if (rows > 1) {
            String message = "添加失败,服务器忙,请稍后再试!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    /**
     * 根据id删除购物车数据
     *
     * @param spuId  要删除的购物车id
     * @param userId 用户id
     */
    @Override
    public void deleteById(Long userId, Long spuId) {
        log.debug("开始处理查询id为{}的购物车业务", spuId);
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("spu_id", spuId);
        Cart queryCart = cartMapper.selectOne(wrapper);
        if (queryCart == null) {
            String message = "删除失败,该购物车数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        int rows = cartMapper.delete(wrapper);
        if (rows > 1) {
            String message = "删除失败,服务器忙,请稍后再试!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    /**
     * 根据用户id查询购物车商品价格总和
     * @param userId 用户id
     * @return 返回价格总和
     */
    @Override
    public Integer selectSUMPrice(Long userId) {
        log.debug("根据用户id查询购物车商品价格总和,参数:{}",userId);
        return cartMapper.selectSUMPrice(userId);
    }

    /**
     * 返回购物车信息
     *
     * @param userId 用户id
     * @return 返回集合
     */
    @Override
    public List<CartListVO> selectCartListByUserId(Long userId) {
        log.debug("开始处理根据用户查询购物车信息的功能,参数:{}", userId);
        return cartMapper.selectCartListByUserId(userId);
    }
}
