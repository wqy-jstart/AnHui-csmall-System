package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.service.ILogisticsService;
import cn.tedu.anhuicsmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 物流的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "14.物流管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/logistics")
public class LogisticsController {

    public LogisticsController(){
        log.debug("创建控制器类:LogisticsController");
    }

    // 注入物流业务层接口
    @Autowired
    private ILogisticsService logisticsService;

    /**
     * 查询物流信息列表
     * @return 返回列表
     */
    @ApiOperation("查询物流信息列表")
    @ApiOperationSupport(order = 500)
    @GetMapping("")
    public JsonResult<List<Object>> selectList(){
        log.debug("开始处理查询物流信息列表的请求,无参!");
        List<Object> objects = logisticsService.selectList();
        return JsonResult.ok(objects);
    }
}
