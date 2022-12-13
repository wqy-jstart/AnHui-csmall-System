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

    public CartServiceImpl(){
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
     * @param cartAddNewDTO 添加的购物车信息
     */
    @Override
    public void insert(CartAddNewDTO cartAddNewDTO) {
        log.debug("开始处理添加购物车数据的功能,参数:{}",cartAddNewDTO);
        // 判断用户名是否存在
        User queryUser = userMapper.selectById(cartAddNewDTO.getUserId());
        if (queryUser == null){
            String message = "添加失败,该用户数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }

        Cart cart = new Cart();
        BeanUtils.copyProperties(cartAddNewDTO,cart);
        int rows = cartMapper.insert(cart);
        if (rows >1){
            String message = "添加失败,服务器忙,请稍后再试!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }
    }

    /**
     * 根据id删除购物车数据
     * @param cartId 要删除的购物车id
     */
    @Override
    public void deleteById(Long cartId) {
        log.debug("开始处理查询id为{}的购物车业务",cartId);
        Cart queryCart = cartMapper.selectById(cartId);
        if (queryCart == null){
            String message = "删除失败,该购物车数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }
        int rows = cartMapper.deleteById(cartId);
        if (rows>1){
            String message = "删除失败,服务器忙,请稍后再试!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE,message);
        }
    }

    @Override
    public List<CartListVO> selectCartListByUserId(Long userId) {
        // 先使用用户id来查询spu的数量(count)和spuId
        List<Long> longs = cartMapper.selectToSpuId(userId);
        // 查询指定用户id下的Spu商品数量
        int count = cartMapper.selectToCount(userId);
        // 定义一个数组,长度为查询的Spu商品数量,类型为id的类型(通常为Long)
        Long[] spuIds = new Long[count];
        // 将利用用户id查询的商品Spu列表数据转换为数组形式(里面含有spuId),转换API--> <T> T[] toArray(T[] a);
        Long[] spuIdToArray = longs.toArray(spuIds);
        // 创建一个最终要返回的List集合
        List<CartListVO> cartListVOS = cartMapper.selectCartListByUserId(userId);
        // 使用增强for循环遍历转换后的数组对象
        for (Long spuId : spuIdToArray) {
            // 随着数组的遍历,利用遍历的每一个spuId(商品id)来查询对应的属性列表
            List<Attribute> attributeList = cartMapper.selectToAttribute(spuId);
            // 增强for遍历最终要返回的集合,在外层的每一次遍历条件下,将属性列表设置到每一个最终要返回的CartListVo对象中
            for (CartListVO cartListVO : cartListVOS) {
                cartListVO.setAttributeList(attributeList);
            }
        }
        // 最终作出返回
        return cartListVOS;
    }
}
