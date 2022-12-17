package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.OrderAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.vo.OrderListVO;
import cn.tedu.anhuicsmall.product.service.IOrderService;
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

import java.nio.file.Path;
import java.util.List;

/**
 * 订单的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "13.订单管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/orders")
public class OrderController {

    public OrderController() {
        log.debug("创建控制器类:OrderController");
    }

    // 注入订单的业务层实现类
    @Autowired
    private IOrderService orderService;

    /**
     * 添加订单的请求
     *
     * @param orderAddNewDTO 添加的订单信息
     * @return 返回结果集
     */
    @ApiOperation("添加订单")
    @ApiOperationSupport(order = 100)
    @PostMapping("/insert")
    public JsonResult<Void> insert(OrderAddNewDTO orderAddNewDTO) {
        log.debug("开始处理添加订单的请求,参数:{}", orderAddNewDTO);
        orderService.insert(orderAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 根据用户id查询未发货的订单数量
     * @param userId 用户id
     * @return 返回结果集
     */
    @ApiOperation("根据用户id查询未发货的订单数量")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long")
    @GetMapping("/{userId:[0-9]+}/selectCountToNotDib")
    public JsonResult<Integer> selectCountToNotDib(@Range(min = 1,message = "查询失败,该购物车id无效")
                                                       @PathVariable Long userId){
        log.debug("开始处理查询用户id为{}的未发货订单数量",userId);
        Integer count = orderService.selectCountToNoDib(userId);
        return JsonResult.ok(count);
    }

    /**
     * 根据用户id查询已发货的订单数量
     * @param userId 用户id
     * @return 返回结果集
     */
    @ApiOperation("根据用户id查询已发货的订单数量")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long")
    @GetMapping("/{userId:[0-9]+}/selectCountToDib")
    public JsonResult<Integer> selectCountToDib(@Range(min = 1,message = "查询失败,该购物车id无效")
                                                   @PathVariable Long userId){
        log.debug("开始处理查询用户id为{}的已发货订单数量",userId);
        Integer count = orderService.selectCountToDib(userId);
        return JsonResult.ok(count);
    }

    /**
     * 根据用户id查询未发货的订单列表
     * @param userId 用户id
     * @return 返回列表
     */
    @ApiOperation("根据用户id查询未发货的订单列表")
    @ApiOperationSupport(order = 500)
    @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long")
    @GetMapping("/{userId:[0-9]+}/selectByUserIdToNoDib")
        public JsonResult<List<OrderListVO>> selectByUserIdToNoDib(@Range(min = 1, message = "查询失败,该用户id无效!")
                                                                       @PathVariable Long userId){
        log.debug("开始处理查询用户id为{}的未发货订单列表",userId);
        List<OrderListVO> orderListVOS = orderService.selectByUserIdToNotDistribute(userId);
        return JsonResult.ok(orderListVOS);
    }

    /**
     * 根据用户id查询已发货的订单列表
     * @param userId 用户id
     * @return 返回列表
     */
    @ApiOperation("根据用户id查询已发货的订单列表")
    @ApiOperationSupport(order = 501)
    @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long")
    @GetMapping("/{userId:[0-9]+}/selectByUserIdToDib")
    public JsonResult<List<OrderListVO>> selectByUserIdToDib(@Range(min = 1, message = "查询失败,该用户id无效!")
                                                               @PathVariable Long userId){
        log.debug("开始处理查询用户id为{}的已发货订单列表",userId);
        List<OrderListVO> orderListVOS = orderService.selectByUserIdToDistribute(userId);
        return JsonResult.ok(orderListVOS);
    }

    /**
     * 处理查询未发货的订单列表信息
     *
     * @return 返回未发货的订单列表
     */
    @ApiOperation("查询未发货的订单列表信息")
    @ApiOperationSupport(order = 502)
    @GetMapping("/selectToNotDistribute")
    public JsonResult<List<OrderListVO>> selectToNotDistribute() {
        log.debug("开始处理查询已发货的订单列表信息,无参!");
        List<OrderListVO> orderListVOS = orderService.selectOrderListToNotDistribute();
        return JsonResult.ok(orderListVOS);
    }

    /**
     * 处理根据用户id和spuId查询未发货的订单信息
     *
     * @return 返回未发货的订单实体类
     */
    @ApiOperation("根据用户id和spuId查询未发货订单信息")
    @ApiOperationSupport(order = 503)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long"),
            @ApiImplicitParam(name = "spuId",value = "spuId",required = true,dataType = "long")
    })
    @GetMapping("/{userId:[0-9]+}/{spuId:[0-9]+}/selectById")
    public JsonResult<OrderListVO> selectToNotDistribute(@Range(min = 1, message = "查询失败,id无效!")
                                                         @PathVariable Long userId,
                                                         @PathVariable Long spuId) {
        log.debug("开始处理根据用户id{}和spuId{}查询已发货的订单信息",userId,spuId);
        OrderListVO orderVO = orderService.selectByIdNoDib(userId, spuId);
        return JsonResult.ok(orderVO);
    }

    /**
     * 处理根据用户id和spuId查询已发货的订单信息
     *
     * @return 返回已发货的订单实体类
     */
    @ApiOperation("根据用户id和spuId查询已发货订单信息")
    @ApiOperationSupport(order = 503)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long"),
            @ApiImplicitParam(name = "spuId",value = "spuId",required = true,dataType = "long")
    })
    @GetMapping("/{userId:[0-9]+}/{spuId:[0-9]+}/selectByIdToDib")
    public JsonResult<OrderListVO> selectToDistribute(@Range(min = 1, message = "查询失败,id无效!")
                                                         @PathVariable Long userId,
                                                         @PathVariable Long spuId) {
        log.debug("开始处理根据用户id{}和spuId{}查询已发货的订单信息",userId,spuId);
        OrderListVO orderVO = orderService.selectById(userId, spuId);
        return JsonResult.ok(orderVO);
    }

    /**
     * 处理退货业务
     * @param orderAddNewDTO 退货信息
     * @return 返回结果集
     */
    @ApiOperation("处理退货业务")
    @ApiOperationSupport(order = 300)
    @PostMapping("/updateToBack")
    public JsonResult<Void> updateToBack(OrderAddNewDTO orderAddNewDTO){
        log.debug("开始处理修改退货业务的请求,参数:{}",orderAddNewDTO);
        orderService.handleBackProduct(orderAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 处理查询已退货的订单列表
     * @return 返回结果集
     */
    @GetMapping("/selectListToBack")
    public JsonResult<List<OrderListVO>> selectListToBack(){
        log.debug("开始处理查询已退货的订单列表");
        List<OrderListVO> orderListVOS = orderService.selectOrderListToBack();
        return JsonResult.ok(orderListVOS);
    }

    /**
     * 根据用户id查询退货列表
     * @param userId 用户id
     * @return 返回结果集
     */
    @ApiOperation("根据用户id查询退货列表")
    @ApiOperationSupport(order = 500)
    @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long")
    @GetMapping("/{userId:[0-9]+}/selectListToBackById")
    public JsonResult<List<OrderListVO>> selectListToBackById(@Range(min = 1,message = "查询失败,该用户id无效!")
                                                                          @PathVariable Long userId){
        log.debug("查询用户id为{}的退货列表",userId);
        List<OrderListVO> orderListVOS = orderService.selectOrderListToBackById(userId);
        return JsonResult.ok(orderListVOS);
    }

    /**
     * 根据用户id查询退货数量
     * @param userId 用户id
     * @return 返回结果集
     */
    @ApiOperation("根据用户id查询退货数量")
    @ApiOperationSupport(order = 500)
    @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long")
    @GetMapping("/{userId:[0-9]+}/selectCountToBack")
    public JsonResult<Integer> selectCountToBack(@Range(min = 1,message = "查询失败,该用户id无效!")
                                                     @PathVariable Long userId){
        log.debug("开始处理查询用户id为{}的退货数量",userId);
        Integer count = orderService.selectOrderCountToBackById(userId);
        return JsonResult.ok(count);
    }

    /**
     * 处理查询已发货的订单列表信息
     *
     * @return 返回已发货的订单列表
     */
    @ApiOperation("查询已发货的订单列表信息")
    @ApiOperationSupport(order = 504)
    @GetMapping("/selectToDistribute")
    public JsonResult<List<OrderListVO>> selectToDistribute() {
        log.debug("开始处理查询已发货的订单列表信息,无参!");
        List<OrderListVO> orderListVOS = orderService.selectOrderListToDistribute();
        return JsonResult.ok(orderListVOS);
    }
}
