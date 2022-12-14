package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.UserLoginDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.UserUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.User;
import cn.tedu.anhuicsmall.product.service.IUserService;
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
 * 这是用户的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "01.用户管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    public UserController() {
        log.debug("创建控制器类:UserController");
    }

    // 注入用户的业务层接口类
    @Autowired
    private IUserService userService;

    /**
     * 处理用户注册的请求
     *
     * @param userLoginDTO 用户注册的信息
     * @return 返回结果集
     */
    @ApiOperation("用户注册")
    @ApiOperationSupport(order = 100)
    @PostMapping("/register")
    public JsonResult<Void> insert(@Valid UserLoginDTO userLoginDTO) {
        log.debug("开始处理用户注册的请求,参数:{}", userLoginDTO);
        userService.insert(userLoginDTO);
        return JsonResult.ok();
    }

    /**
     * 处理用户登录的请求
     * @param userLoginDTO 用户登录的信息
     * @return 返回结果集
     */
    @ApiOperation("用户登录")
    @ApiOperationSupport(order = 120)
    @PostMapping("/login")
    public JsonResult<String> login(UserLoginDTO userLoginDTO){
        log.debug("开始处理用户登录的请求,参数:{}",userLoginDTO);
        String jwt = userService.login(userLoginDTO);
        return JsonResult.ok(jwt);
    }

    /**
     * 根据id删除用户的请求
     *
     * @param userId 要删除的用户id
     * @return 返回结果集
     */
    @ApiOperation("根据id删除用户")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "long")
    @PostMapping("/{userId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@Range(min = 1,message = "删除失败,该用户id无效!")
                                           @PathVariable Long userId) {
        log.debug("开始处理删除id为[{}]的用户", userId);
        userService.deleteById(userId);
        return JsonResult.ok();
    }

    /**
     * 处理修改用户信息的请求
     * @param userUpdateDTO 用户修改的信息
     * @return 返回结果集
     */
    @ApiOperation("根据id修改用户信息")
    @ApiOperationSupport(order = 300)
    @PostMapping("/update")
    public JsonResult<Void> update(UserUpdateDTO userUpdateDTO){
        log.debug("开始处理修改id为{}的用户信息的请求",userUpdateDTO.getId());
        userService.update(userUpdateDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id查询用户详情的请求
     * @param userId 用户id
     * @return 返回结果集
     */
    @ApiOperation("根据id查询用户详情")
    @ApiOperationSupport(order = 501)
    @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "long")
    @GetMapping("/{userId:[0-9]+}/selectById")
    public JsonResult<User> selectById(@Range(min = 1,message = "查询失败,该用户id无效") @PathVariable Long userId){
        log.debug("开始处理查询id为[{}]的用户详情",userId);
        User user = userService.selectById(userId);
        return JsonResult.ok(user);
    }

    /**
     * 根据用户名查询用户详情的请求
     * @param username 用户id
     * @return 返回结果集
     */
    @ApiOperation("根据用户名查询用户详情")
    @ApiOperationSupport(order = 502)
    @GetMapping("/selectByUsername")
    public JsonResult<User> selectById(@RequestParam(value = "username") String username){
        log.debug("开始处理查询用户名为[{}]的用户详情",username);
        User user = userService.selectByUserName(username);
        return JsonResult.ok(user);
    }

    /**
     * 查询用户列表的请求
     *
     * @return 返回结果集
     */
    @ApiOperation("查询用户列表")
    @ApiOperationSupport(order = 503)
    @GetMapping("")
    public JsonResult<List<Object>> selectList() {
        log.debug("开始处理查询用户列表的请求...无参!");
        List<Object> objects = userService.selectList();
        return JsonResult.ok(objects);
    }
}
