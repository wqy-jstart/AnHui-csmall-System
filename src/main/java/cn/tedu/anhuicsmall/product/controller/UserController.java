package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.UserLoginDTO;
import cn.tedu.anhuicsmall.product.service.IUserService;
import cn.tedu.anhuicsmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 这是用户的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "01.用户商品模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    public UserController(){
        log.debug("创建控制器类:UserController");
    }

    @Autowired
    private IUserService userService;

    /**
     * 处理用户注册的请求
     * @param userLoginDTO 用户注册的信息
     * @return 返回结果集
     */
    @ApiOperation("用户注册")
    @ApiOperationSupport(order = 100)
    @PostMapping("/insert")
    public JsonResult<Void> insert(UserLoginDTO userLoginDTO){
        log.debug("开始处理用户注册的请求,参数:{}",userLoginDTO);
        userService.insert(userLoginDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id删除用户的请求
     * @param userId 要删除的用户id
     * @return 返回结果集
     */
    @ApiOperation("根据id删除用户")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(value = "userId",name = "用户id",required = true,dataType = "long")
    @PostMapping("/{userId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@PathVariable Long userId){
        log.debug("开始处理删除id为[{}]的用户",userId);
        userService.deleteById(userId);
        return JsonResult.ok();
    }

    /**
     * 查询用户列表的请求
     * @return 返回结果集
     */
    @ApiOperation("查询用户列表")
    @ApiOperationSupport(order = 500)
    @GetMapping("")
    public JsonResult<List<Object>> selectList(){
        log.debug("开始处理查询用户列表的请求...无参!");
        List<Object> objects = userService.selectList();
        return JsonResult.ok(objects);
    }
}
