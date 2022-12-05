package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.AddressAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AddressUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Address;
import cn.tedu.anhuicsmall.product.service.IAddressService;
import cn.tedu.anhuicsmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.License;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户收货地址的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "02.收货地址模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/addresses")
public class AddressController {

    public AddressController() {
        log.debug("创建控制器类:AddressController");
    }

    // 注入收货地址的业务层接口类
    @Autowired
    private IAddressService addressService;

    /**
     * 处理添加收货地址的请求
     *
     * @param addressAddNewDTO 添加的收货地址信息
     * @return 返回结果集
     */
    @ApiOperation("添加收货地址")
    @ApiOperationSupport(order = 100)
    @PostMapping("insert")
    public JsonResult<Void> insert(@Valid AddressAddNewDTO addressAddNewDTO) {
        log.debug("开始处理添加收货地址的业务,参数:{}", addressAddNewDTO);
        addressService.insert(addressAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 处理根据id删除收货地址信息的请求
     *
     * @param addressId 要删除的收货地址id
     * @return 返回结果集
     */
    @ApiOperation("根据id删除收货地址信息")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "addressId", value = "收货地址id", required = true, dataType = "long")
    @PostMapping("/{addressId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@Range(min = 1, message = "删除失败,该收货地址id无效!")
                                       @PathVariable Long addressId) {
        log.debug("开始处理删除id为{}的收货地址信息", addressId);
        addressService.deleteById(addressId);
        return JsonResult.ok();
    }

    /**
     * 根据id修改收货地址的信息
     *
     * @param addressUpdateDTO 接收要修改的收货地址信息
     * @return 返回结果集
     */
    @ApiOperation("根据id修改收货地址信息")
    @ApiOperationSupport(order = 300)
    @PostMapping("/update")
    public JsonResult<Void> update(AddressUpdateDTO addressUpdateDTO) {
        log.debug("开始处理修改id为{}的收货地址信息", addressUpdateDTO.getId());
        addressService.update(addressUpdateDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id查询收货地址id的详情信息
     *
     * @param addressId 收货地址id
     * @return 返回结果集
     */
    @ApiOperation("根据id查询收货地址详情")
    @ApiOperationSupport(order = 500)
    @ApiImplicitParam(name = "addressId", value = "收货地址id", required = true, dataType = "long")
    @PostMapping("/{addressId:[0-9]+}/selectById")
    public JsonResult<Address> selectById(@Range(min = 1, message = "查询失败,该收货地址id无效!")
                                          @PathVariable Long addressId) {
        log.debug("开始处理查询id为{}的收货地址信息", addressId);
        Address address = addressService.selectById(addressId);
        return JsonResult.ok(address);
    }

    /**
     * 处理查询收货地址列表的功能
     *
     * @return 返回结果集
     */
    @ApiOperation("查询收货地址列表")
    @ApiOperationSupport(order = 502)
    @GetMapping("")
    public JsonResult<List<Object>> selectList() {
        log.debug("开始处理查询收货地址列表的请求,无参!");
        List<Object> objects = addressService.selectList();
        return JsonResult.ok(objects);
    }
}
