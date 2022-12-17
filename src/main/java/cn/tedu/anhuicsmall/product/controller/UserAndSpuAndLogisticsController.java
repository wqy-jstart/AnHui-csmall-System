package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.UserAndSpuAndLogisticsAddNewDTO;
import cn.tedu.anhuicsmall.product.service.IUserAndSpuAndLogisticsService;
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

/**
 * 处理发货添加管理信息的控制器
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "发货管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/usl")
public class UserAndSpuAndLogisticsController {

    public UserAndSpuAndLogisticsController(){
        log.debug("创建控制器类:UserAndSpuAndLogisticsController");
    }

    // 注入关联表的持久层接口
    @Autowired
    private IUserAndSpuAndLogisticsService uslService;

    /**
     * 处理发货添加关联信息的请求
     * @param uslAddNewDTO 插入的数据
     * @return 返回结果集
     */
    @PostMapping("/insert")
    public JsonResult<Void> insert(UserAndSpuAndLogisticsAddNewDTO uslAddNewDTO){
        log.debug("开始处理添加用户,Spu,物流的关联信息,参数:{}",uslAddNewDTO);
        uslService.insert(uslAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 处理退货的请求
     * @param userId 用户id
     * @param spuId spuId
     * @return 返回结果集
     */
    @ApiOperation("处理退货的请求")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long"),
            @ApiImplicitParam(name = "spuId",value = "spuId",required = true,dataType = "long")
    })
    @GetMapping("/{userId:[0-9]+}/{spuId:[0-9]+}/deleteToBack")
    public JsonResult<Void> deleteToBack(@Range(min = 1,message = "操作失败,该id无效!")
                                         @PathVariable Long userId,
                                         @PathVariable Long spuId){
        log.debug("开始处理根据用户id和spuId删除订单的请求");
        uslService.deleteToBack(userId,spuId);
        return JsonResult.ok();
    }
}
