package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.BrandAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.BrandUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Brand;
import cn.tedu.anhuicsmall.product.service.IBrandService;
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
 * 品牌的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "06.品牌管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/brands")
public class BrandController {

    public BrandController() {
        log.debug("创建控制器对象:BrandController");
    }

    // 注入品牌的业务层接口
    @Autowired
    private IBrandService brandService;

    /**
     * 处理添加品牌的请求
     *
     * @param brandAddNewDTO 添加的品牌信息
     * @return 返回结果集
     */
    @ApiOperation("添加品牌")
    @ApiOperationSupport(order = 100)
    @PostMapping("/insert")
    public JsonResult<Void> insert(@Valid BrandAddNewDTO brandAddNewDTO) {
        log.debug("开始处理添加品牌的请求,参数:{}", brandAddNewDTO);
        brandService.insert(brandAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id删除品牌
     *
     * @param brandId 要删除的品牌id
     * @return 返回结果集
     */
    @ApiOperation("根据id删除品牌")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "brandId", value = "品牌id", required = true, dataType = "long")
    @PostMapping("/{brandId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@Range(min = 1, message = "删除失败,该品牌id无效!")
                                       @PathVariable Long brandId) {
        log.debug("开始处理删除id为{}的品牌请求", brandId);
        brandService.deleteById(brandId);
        return JsonResult.ok();
    }

    /**
     * 根据id修改品牌的请求
     *
     * @param brandUpdateDTO 要修改的品牌数据
     * @return 返回结果集
     */
    @ApiOperation("根据id修改品牌")
    @ApiOperationSupport(order = 300)
    @PostMapping("/update")
    public JsonResult<Void> update(BrandUpdateDTO brandUpdateDTO) {
        log.debug("开始处理修改id为{}的品牌请求", brandUpdateDTO.getId());
        brandService.update(brandUpdateDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id查询品牌详情
     *
     * @param brandId 要查询的品牌id
     * @return 返回结果集
     */
    @ApiOperation("根据id查询品牌详情")
    @ApiOperationSupport(order = 500)
    @ApiImplicitParam(name = "brandId", value = "品牌id", required = true, dataType = "long")
    @GetMapping("/{brandId:[0-9]+}/selectById")
    public JsonResult<Brand> selectById(@Range(min = 1, message = "查询失败,该品牌id无效!")
                                        @PathVariable Long brandId) {
        log.debug("开始查询id为{}的品牌数据", brandId);
        Brand brand = brandService.selectById(brandId);
        return JsonResult.ok(brand);
    }

    /**
     * 查询品牌列表的请求
     *
     * @return 返回列表集合
     */
    @ApiOperation("查询品牌列表")
    @ApiOperationSupport(order = 501)
    @GetMapping("")
    public JsonResult<List<Brand>> selectList() {
        log.debug("开始处理查询品牌列表的请求,无参!");
        List<Brand> objects = brandService.selectList();
        return JsonResult.ok(objects);
    }

    /**
     * 启用分类
     * @param brandId 需要启用的品牌id
     * @return JsonResult
     */
    // http://localhost:9900/brands/brandId/enable
    @ApiOperation("启用品牌")
    @ApiOperationSupport(order = 503)
    @ApiImplicitParam(name = "brandId",value = "启用的品牌id",required = true,dataType = "long")
    @PostMapping("/{brandId:[0-9]+}/enable")
    public JsonResult<Void> enable(@Range(min = 1,message = "启用品牌失败,该id无效!")
                                   @PathVariable Long brandId){
        log.debug("开始启用品牌!");
        brandService.setEnable(brandId);
        return JsonResult.ok();
    }

    /**
     * 禁用品牌
     * @param brandId 需要禁用的品牌id
     * @return JsonResult
     */
    // http://localhost:9900/brands/brandId/disable
    @ApiOperation("禁用分类")
    @ApiOperationSupport(order = 504)
    @ApiImplicitParam(name = "brandId",value = "禁用的品牌id",required = true,dataType = "long")
    @PostMapping("/{brandId:[0-9]+}/disable")
    public JsonResult<Void> disable(@Range(min = 1,message = "禁用品牌失败,该id无效!")
                                    @PathVariable Long brandId){
        log.debug("开始禁用品牌!");
        brandService.setDisable(brandId);
        return JsonResult.ok();
    }
}
