package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.OrderAddNewDTO;
import cn.tedu.anhuicsmall.product.service.IOrderService;
import cn.tedu.anhuicsmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public OrderController(){
        log.debug("创建控制器类:OrderController");
    }

    // 注入订单的业务层实现类
    @Autowired
    private IOrderService orderService;

    /**
     * 添加订单的请求
     * @param orderAddNewDTO 添加的订单信息
     * @return 返回结果集
     */
    @ApiOperation("添加订单")
    @ApiOperationSupport(order = 100)
    @PostMapping("/insert")
    public JsonResult<Void> insert(OrderAddNewDTO orderAddNewDTO){
        log.debug("开始处理添加订单的请求,参数:{}",orderAddNewDTO);
        orderService.insert(orderAddNewDTO);
        return JsonResult.ok();
    }
}
