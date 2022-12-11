package cn.tedu.anhuicsmall.product.controller;


import cn.tedu.anhuicsmall.product.pojo.dto.SpuAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.SpuUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Spu;
import cn.tedu.anhuicsmall.product.pojo.vo.SpuIndexListVO;
import cn.tedu.anhuicsmall.product.service.ISpuService;
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

import java.util.List;

/**
 * 商品Spu的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "10.商品Spu管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/spu")
public class SpuController {

    public SpuController() {
        log.debug("创建控制器类:SpuController");
    }

    // 注入Spu的业务层接口实现类
    @Autowired
    private ISpuService spuService;

    /**
     * 处理添加Spu的请求
     *
     * @param spuAddNewDTO 添加的请求信息
     * @return 返回结果集
     */
    @ApiOperation("添加Spu")
    @ApiOperationSupport(order = 100)
    @PostMapping("/insert")
    public JsonResult<Void> insert(SpuAddNewDTO spuAddNewDTO) {
        log.debug("开始处理添加Spu的请求,参数:{}", spuAddNewDTO);
        spuService.insert(spuAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id删除Spu的请求
     *
     * @param spuId 要删除的SpuId
     * @return 返回结果集
     */
    @ApiOperation("根据id删除Spu")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "spuId", value = "spuId", required = true, dataType = "long")
    @PostMapping("/{spuId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@Range(min = 1, message = "删除失败,该SpuId无效!")
                                       @PathVariable Long spuId) {
        log.debug("开始处理删除id为{}的Spu数据的请求", spuId);
        spuService.deleteById(spuId);
        return JsonResult.ok();
    }

    /**
     * 处理根据id修改Spu的请求
     *
     * @param spuUpdateDTO 修改的信息
     * @return 返回结果集
     */
    @ApiOperation("根据id修改Spu")
    @ApiOperationSupport(order = 300)
    @PostMapping("/update")
    public JsonResult<Void> update(SpuUpdateDTO spuUpdateDTO) {
        log.debug("开始处理修改id为{}的Spu数据的请求", spuUpdateDTO.getId());
        spuService.update(spuUpdateDTO);
        return JsonResult.ok();
    }

    /**
     * 处理根据id查询Spu数据的请求
     *
     * @param spuId 要查询的SpuId
     * @return 返回结果集
     */
    @ApiOperation("根据id查询Spu数据")
    @ApiOperationSupport(order = 500)
    @ApiImplicitParam(name = "spuId", value = "spuId", required = true, dataType = "long")
    @GetMapping("/{spuId:[0-9]+}/selectById")
    public JsonResult<Spu> selectById(@Range(min = 1, message = "查询失败,该SpuId无效!")
                                      @PathVariable Long spuId) {
        log.debug("开始处理查询id为{}的Spu数据", spuId);
        Spu spu = spuService.selectById(spuId);
        return JsonResult.ok(spu);
    }

    /**
     * 处理查询主页Spu列表的请求
     * @return 返回Spu主页列表信息
     */
    @ApiOperation("查询主页Spu列表")
    @ApiOperationSupport(order = 501)
    @GetMapping("/selectIndexList")
    public JsonResult<List<SpuIndexListVO>> selectIndexList(){
        log.debug("开始处理查询Spu主页列表的信息,无参!");
        List<SpuIndexListVO> spuIndexListVOS = spuService.selectIndexSpu();
        return JsonResult.ok(spuIndexListVOS);
    }

    /**
     * 查询未上架的SPU列表
     *
     * @return 返回上架的Spu列表
     */
    @ApiOperation("查询未上架的SPU列表")
    @ApiOperationSupport(order = 502)
    @GetMapping("/selectByNotP")
    public JsonResult<List<Object>> selectByP() {
        log.debug("开始处理查询未上架的Spu列表,无参!");
        List<Object> objects = spuService.selectByNotIsP();
        return JsonResult.ok(objects);
    }

    /**
     * 查询上架及审核的SPU列表
     *
     * @return 返回上架及审核的Spu列表
     */
    @ApiOperation("查询上架及审核的SPU列表")
    @ApiOperationSupport(order = 503)
    @GetMapping("/selectByPC")
    public JsonResult<List<Object>> selectByPC() {
        log.debug("开始处理查询上架及审核的Spu列表,无参!");
        List<Object> objects = spuService.selectByIsPC();
        return JsonResult.ok(objects);
    }

    /**
     * 设置上架spu
     *
     * @param spuId 需要上架的spuId
     * @return JsonResult
     */
    @ApiOperation("设置上架Spu")
    @ApiOperationSupport(order = 504)
    @ApiImplicitParam(name = "spuId", value = "上架的spuId", required = true, dataType = "long")
    @PostMapping("/{spuId:[0-9]+}/publish")
    public JsonResult<Void> setPublish(@Range(min = 1, message = "上架Spu失败,该id无效!")
                                       @PathVariable Long spuId) {
        log.debug("开始处理上架Spu的请求!");
        spuService.setPublish(spuId);
        return JsonResult.ok();
    }

    /**
     * 设置下架spu
     *
     * @param spuId 需要下架的spuId
     * @return JsonResult
     */
    @ApiOperation("设置下架Spu")
    @ApiOperationSupport(order = 505)
    @ApiImplicitParam(name = "spuId", value = "下架的spuId", required = true, dataType = "long")
    @PostMapping("/{spuId:[0-9]+}/notPublish")
    public JsonResult<Void> setNotPublish(@Range(min = 1, message = "下架Spu失败,该id无效!")
                                          @PathVariable Long spuId) {
        log.debug("开始处理下架Spu的请求!");
        spuService.setNotPublish(spuId);
        return JsonResult.ok();
    }

    /**
     * 设置推荐spu
     *
     * @param spuId 需要推荐的spuId
     * @return JsonResult
     */
    @ApiOperation("设置推荐Spu")
    @ApiOperationSupport(order = 506)
    @ApiImplicitParam(name = "spuId", value = "推荐的spuId", required = true, dataType = "long")
    @PostMapping("/{spuId:[0-9]+}/recommend")
    public JsonResult<Void> setRecommend(@Range(min = 1, message = "推荐Spu失败,该id无效!")
                                     @PathVariable Long spuId) {
        log.debug("开始处理推荐Spu的请求!");
        spuService.setRecommend(spuId);
        return JsonResult.ok();
    }

    /**
     * 设置不推荐spu
     *
     * @param spuId 需要不推荐的spuId
     * @return JsonResult
     */
    @ApiOperation("设置不推荐Spu")
    @ApiOperationSupport(order = 507)
    @ApiImplicitParam(name = "spuId", value = "不推荐的spuId", required = true, dataType = "long")
    @PostMapping("/{spuId:[0-9]+}/notRecommend")
    public JsonResult<Void> setNotRecommend(@Range(min = 1, message = "不推荐Spu失败,该id无效!")
                                         @PathVariable Long spuId) {
        log.debug("开始处理不推荐Spu的请求!");
        spuService.setNotRecommend(spuId);
        return JsonResult.ok();
    }

    /**
     * 设置审核spu
     *
     * @param spuId 需要审核的spuId
     * @return JsonResult
     */
    @ApiOperation("设置审核Spu")
    @ApiOperationSupport(order = 508)
    @ApiImplicitParam(name = "spuId", value = "审核的spuId", required = true, dataType = "long")
    @PostMapping("/{spuId:[0-9]+}/check")
    public JsonResult<Void> setCheck(@Range(min = 1, message = "审核Spu失败,该id无效!")
                                     @PathVariable Long spuId) {
        log.debug("开始处理审核Spu的请求!");
        spuService.setCheck(spuId);
        return JsonResult.ok();
    }

    /**
     * 设置未审核spu
     *
     * @param spuId 需要设置未审核的spuId
     * @return JsonResult
     */
    @ApiOperation("设置未审核的Spu")
    @ApiOperationSupport(order = 509)
    @ApiImplicitParam(name = "spuId", value = "未审核的spuId", required = true, dataType = "long")
    @PostMapping("/{spuId:[0-9]+}/notCheck")
    public JsonResult<Void> setNotCheck(@Range(min = 1, message = "设置未审核Spu失败,该id无效!")
                                        @PathVariable Long spuId) {
        log.debug("开始处理设置未审核Spu的请求!");
        spuService.setNotCheck(spuId);
        return JsonResult.ok();
    }
}
