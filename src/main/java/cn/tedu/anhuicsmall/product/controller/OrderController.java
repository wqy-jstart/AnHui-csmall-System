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
     * 处理查询未发货的订单列表信息
     *
     * @return 返回未发货的订单列表
     */
    @ApiOperation("查询未发货的订单列表信息")
    @ApiOperationSupport(order = 500)
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
    @ApiOperation("根据用户id和spuId查询订单信息")
    @ApiOperationSupport(order = 501)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long"),
            @ApiImplicitParam(name = "spuId",value = "spuId",required = true,dataType = "long")
    })
    @GetMapping("/{userId:[0-9]+}/{spuId:[0-9]+}/selectById")
    public JsonResult<OrderListVO> selectToNotDistribute(@Range(min = 1, message = "查询失败,id无效!")
                                                         @PathVariable Long userId,
                                                         @PathVariable Long spuId) {
        log.debug("开始处理根据用户id{}和spuId{}查询已发货的订单信息",userId,spuId);
        OrderListVO orderVO = orderService.selectById(userId, spuId);
        return JsonResult.ok(orderVO);
    }

    /**
     * 处理查询已发货的订单列表信息
     *
     * @return 返回已发货的订单列表
     */
    @ApiOperation("查询已发货的订单列表信息")
    @ApiOperationSupport(order = 502)
    @GetMapping("/selectToDistribute")
    public JsonResult<List<OrderListVO>> selectToDistribute() {
        log.debug("开始处理查询已发货的订单列表信息,无参!");
        List<OrderListVO> orderListVOS = orderService.selectOrderListToDistribute();
        return JsonResult.ok(orderListVOS);
    }
}
