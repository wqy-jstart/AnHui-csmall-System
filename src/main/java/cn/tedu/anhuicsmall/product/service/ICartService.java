package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.CartAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Cart;
import cn.tedu.anhuicsmall.product.pojo.vo.CartListVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 购物车的业务层接口
 *
 * @Author Java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface ICartService extends IService<Cart> {

    /**
     * 添加购物车的功能
     * @param cartAddNewDTO 添加的购物车信息
     */
    void insert(CartAddNewDTO cartAddNewDTO);

    /**
     * 根据id删除购物车的功能
     * @param cartId 要删除的购物车id
     */
    void deleteById(Long cartId);

    /**
     * 根据用户id查询购物车列表
     * @param userId 用户id
     * @return 返回购物车列表
     */
    List<CartListVO> selectCartListByUserId(Long userId);
}
