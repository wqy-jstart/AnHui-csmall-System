package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.BannerAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.BannerUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Banner;
import cn.tedu.anhuicsmall.product.service.IBannerService;
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
 * 轮播图的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "09.轮播图管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/banners")
public class BannerController {

    public BannerController(){
        log.debug("创建控制器类:BannerController");
    }

    // 注入轮播图业务层接口类
    @Autowired
    private IBannerService bannerService;

    /**
     * 添加轮播图
     * @param bannerAddNewDTO 添加的轮播图信息
     * @return 返回结果集
     */
    @ApiOperation("添加轮播图")
    @ApiOperationSupport(order = 100)
    @PostMapping("/insert")
    public JsonResult<Void> insert(@Valid BannerAddNewDTO bannerAddNewDTO){
        log.debug("开始处理添加轮播图的请求,参数:{}",bannerAddNewDTO);
        bannerService.insert(bannerAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id删除轮播图
     * @param bannerId 轮播图id
     * @return 返回结果集
     */
    @ApiOperation("根据id删除轮播图")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "bannerId",value = "轮播图id",required = true,dataType = "long")
    @PostMapping("/{bannerId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@Range(min = 1,message = "删除失败,该轮播图id无效!")
                                       @PathVariable Long bannerId){
        log.debug("开始处理删除id为{}的轮播图数据",bannerId);
        bannerService.deleteById(bannerId);
        return JsonResult.ok();
    }

    /**
     * 根据id修改轮播图数据
     * @param bannerUpdateDTO 修改的轮播图数据
     * @return 返回结果集
     */
    @ApiOperation("根据id修改轮播图")
    @ApiOperationSupport(order = 300)
    @PostMapping("/update")
    public JsonResult<Void> update(BannerUpdateDTO bannerUpdateDTO){
        log.debug("开始处理修改id为{}的轮播图数据",bannerUpdateDTO.getId());
        bannerService.update(bannerUpdateDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id查询轮播图信息
     * @param bannerId 轮播图id
     * @return 返回结果集
     */
    @ApiOperation("根据id查询轮播图数据")
    @ApiOperationSupport(order = 500)
    @ApiImplicitParam(name = "bannerId",value = "轮播图id",required = true,dataType = "long")
    @GetMapping("/{bannerId:[0-9]+}/selectById")
    public JsonResult<Banner> selectById(@Range(min = 1,message = "查询失败,该轮播图id无效!")
                                         @PathVariable Long bannerId){
        log.debug("开始处理查询id为{}的轮播图数据",bannerId);
        Banner banner = bannerService.selectById(bannerId);
        return JsonResult.ok(banner);
    }

    /**
     * 处理查询轮播图列表的请求
     * @return 返回结果集
     */
    @ApiOperation("查询轮播图列表")
    @ApiOperationSupport(order = 501)
    @GetMapping("")
    public JsonResult<List<Object>> selectList(){
        log.debug("开始处理查询轮播图列表的请求,无参!");
        List<Object> objects = bannerService.selectList();
        return JsonResult.ok(objects);
    }

    /**
     * 启用轮播图
     * @param bannerId 需要启用的轮播图id
     * @return JsonResult
     */
    @ApiOperation("启用轮播图")
    @ApiOperationSupport(order = 503)
    @ApiImplicitParam(name = "bannerId",value = "启用的轮播图id",required = true,dataType = "long")
    @PostMapping("/{bannerId:[0-9]+}/enable")
    public JsonResult<Void> enable(@Range(min = 1,message = "启用轮播图失败,该id无效!")
                                   @PathVariable Long bannerId){
        log.debug("开始启用轮播图!");
        bannerService.setEnable(bannerId);
        return JsonResult.ok();
    }

    /**
     * 禁用轮播图
     * @param bannerId 需要禁用的轮播图id
     * @return JsonResult
     */
    @ApiOperation("禁用轮播图")
    @ApiOperationSupport(order = 504)
    @ApiImplicitParam(name = "bannerId",value = "禁用的轮播图id",required = true,dataType = "long")
    @PostMapping("/{bannerId:[0-9]+}/disable")
    public JsonResult<Void> disable(@Range(min = 1,message = "禁用轮播图失败,该id无效!")
                                    @PathVariable Long bannerId){
        log.debug("开始禁用轮播图!");
        bannerService.setDisable(bannerId);
        return JsonResult.ok();
    }
}
