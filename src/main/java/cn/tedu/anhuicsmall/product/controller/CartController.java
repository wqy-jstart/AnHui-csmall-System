package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.CartAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.vo.CartListVO;
import cn.tedu.anhuicsmall.product.service.ICartService;
import cn.tedu.anhuicsmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 购物车控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "11.购物车管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/carts")
public class CartController {

    public CartController(){
        log.debug("创建控制器类:artController");
    }

    // 注入购物车业务层接口
    @Autowired
    private ICartService cartService;

    /**
     * 处理添加购物车的请求
     * @param cartAddNewDTO 添加的数据
     * @return 返回结果集
     */
    @ApiOperation("添加购物车")
    @ApiOperationSupport(order = 100)
    @PostMapping("insert")
    public JsonResult<Void> insert(@Valid CartAddNewDTO cartAddNewDTO){
        log.debug("开始处理添加购物车的请求,参数:{}",cartAddNewDTO);
        cartService.insert(cartAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id删除购物车数据
     * @param cartId 要删除的购物车id
     * @return 返回结果集
     */
    @ApiOperation("根据id删除购物车")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "cartId",value = "购物车id",required = true,dataType = "long")
    @PostMapping("/{cartId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@Range(min = 1,message = "删除失败,该购物车id无效")
                                       @PathVariable Long cartId){
        log.debug("开始处理删除id为{}的购物车数据",cartId);
        cartService.deleteById(cartId);
        return JsonResult.ok();
    }


    /**
     * 根据用户id查询购物车列表信息
     * @param userId 用户id
     * @return 返回结果集
     */
    @ApiOperation("根据用户id查询购物车列表数据")
    @ApiOperationSupport(order = 500)
    @GetMapping("/selectToCartList")
    public JsonResult<List<CartListVO>> selectToCartList(@RequestParam(value = "id") Long userId){
        log.debug("开始处理根据用户id查询购物车列表数据");
        List<CartListVO> cartListVOS = cartService.selectCartListByUserId(userId);
        return JsonResult.ok(cartListVOS);
    }
}
