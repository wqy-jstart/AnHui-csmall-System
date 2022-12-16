package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.UserAndSpuAndLogisticsAddNewDTO;
import cn.tedu.anhuicsmall.product.service.IUserAndSpuAndLogisticsService;
import cn.tedu.anhuicsmall.product.web.JsonResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
