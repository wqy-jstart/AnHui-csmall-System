package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.CartAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.vo.CartListVO;
import cn.tedu.anhuicsmall.product.service.ICartService;
import cn.tedu.anhuicsmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
        log.debug("创建控制器类:CartController");
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
     * 根据用户id查询购物车商品价格总和
     * @param userId 用户id
     * @return 返回数据
     */
    @ApiOperation("查询购物车商品价格总和")
    @ApiOperationSupport(order = 150)
    @GetMapping("/selectSUMPrice")
    public JsonResult<Integer> selectSUMPrice(@RequestParam(value = "id") Long userId){
        log.debug("开始处理根据用户id查询购物车商品价格总和,参数:{}",userId);
        Integer integer = cartService.selectSUMPrice(userId);
        return JsonResult.ok(integer);
    }

    /**
     * 根据id删除购物车数据
     * @param userId 用户id
     * @param cartId 要删除的购物车id
     * @return 返回结果集
     */
    @ApiOperation("根据id删除购物车")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long"),
            @ApiImplicitParam(name = "cartId",value = "购物车id",required = true,dataType = "long"),
    })
    @PostMapping("/{userId:[0-9]+}/{cartId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@Range(min = 1,message = "删除失败,该购物车id无效")
                                       @PathVariable Long userId,
                                       @PathVariable Long cartId){
        log.debug("开始处理删除id为{}的用户的购物车id为{}的数据",userId,cartId);
        cartService.deleteById(userId,cartId);
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
        log.debug("返回的数据:{}",cartListVOS);
        return JsonResult.ok(cartListVOS);
    }

    /**
     * 根据用户id查询购物车数量
     * @param userId 用户id
     * @return 返回购物车数量
     */
    @ApiOperation("根据用户id查询购物车数量")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long")
    @GetMapping("/{userId:[0-9]+}/selectCount")
    public JsonResult<Integer> selectByCount(@Range(min = 1,message = "查询失败,该购物车id无效")
                                                 @PathVariable Long userId){
        log.debug("开始处理根据用户id[{}]查询购物车数量的请求",userId);
        Integer count = cartService.selectCount(userId);
        return JsonResult.ok(count);
    }
}
