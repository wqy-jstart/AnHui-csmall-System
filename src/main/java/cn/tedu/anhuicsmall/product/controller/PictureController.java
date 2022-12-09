package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.PictureAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.PictureUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Picture;
import cn.tedu.anhuicsmall.product.service.IPictureService;
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
 * 相册的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "08.图片管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/pictures")
public class PictureController {

    public PictureController(){
        log.debug("创建控制器类:PictureController");
    }

    // 注入图片业务层接口
    @Autowired
    private IPictureService pictureService;

    /**
     * 处理添加图片的请求
     * @param pictureAddNewDTO 添加的图片信息
     * @return 返回结果集
     */
    @ApiOperation("添加图片")
    @ApiOperationSupport(order = 100)
    @PostMapping("/insert")
    public JsonResult<Void> insert(PictureAddNewDTO pictureAddNewDTO){
        log.debug("开始处理添加图片的请求,参数:{}",pictureAddNewDTO);
        pictureService.insert(pictureAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id删除图片
     * @param pictureId 要删除的图片id
     * @return 返回结果集
     */
    @ApiOperation("根据id删除图片")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "pictureId",value = "图片id",required = true,dataType = "long")
    @PostMapping("/{pictureId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@Range(min = 1,message = "删除失败,该图片id无效")
                                       @PathVariable Long pictureId){
        log.debug("开始处理删除id为{}的图片信息",pictureId);
        pictureService.deleteById(pictureId);
        return JsonResult.ok();
    }

    /**
     * 根据id修改图片信息
     * @param pictureUpdateDTO 修改的图片信息
     * @return 返回结果集
     */
    @ApiOperation("根据id修改图片")
    @ApiOperationSupport(order = 300)
    @PostMapping("/update")
    public JsonResult<Void> update(PictureUpdateDTO pictureUpdateDTO){
        log.debug("开始处理修改id为{}的图片信息",pictureUpdateDTO.getId());
        pictureService.update(pictureUpdateDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id查询图片信息
     * @param pictureId 查询的图片id
     * @return 返回结果集
     */
    @ApiOperation("根据id查询图片信息")
    @ApiOperationSupport(order = 500)
    @ApiImplicitParam(name = "pictureId",value = "图片id",required = true,dataType = "long")
    @GetMapping("/{pictureId:[0-9]+}/selectById")
    public JsonResult<Picture> selectById(@Range(min = 1,message = "查询失败,该图片id无效!")
                                          @PathVariable Long pictureId){
        log.debug("开始处理查询id为{}的图片信息",pictureId);
        Picture picture = pictureService.selectById(pictureId);
        return JsonResult.ok(picture);
    }

    /**
     * 查询图片列表的请求
     * @return 返回列表的集合
     */
    @ApiOperation("查询图片所有列表")
    @ApiOperationSupport(order = 501)
    @GetMapping("")
    public JsonResult<List<Object>> selectList(){
        log.debug("开始处理查询图片列表的请求,无参!");
        List<Object> objects = pictureService.selectList();
        return JsonResult.ok(objects);
    }

    /**
     * 根据相册id查询图片列表
     * @param albumId 相册id
     * @return 返回查询的列表集合
     */
    @ApiOperation("根据相册id查询图片列表")
    @ApiOperationSupport(order = 502)
    @GetMapping("/selectListToAlbumId")
    public JsonResult<List<Object>> selectListByAlbumId(@RequestParam(value = "albumId") Long albumId){
        log.debug("开始处理查询相册id为{}下的图片列表!",albumId);
        List<Object> objects = pictureService.selectListByAlbumId(albumId);
        return JsonResult.ok(objects);
    }

    /**
     * 设置图片为封面
     * @param pictureId 需要设置的图片id
     * @return JsonResult
     */
    @ApiOperation("设置图片为封面")
    @ApiOperationSupport(order = 503)
    @ApiImplicitParam(name = "pictureId",value = "封面的图片id",required = true,dataType = "long")
    @PostMapping("/{pictureId:[0-9]+}/isCover")
    public JsonResult<Void> isCover(@Range(min = 1,message = "设置失败,该图片id无效!")
                                   @PathVariable Long pictureId){
        log.debug("开始将id为{}的图片设置为封面!",pictureId);
        pictureService.setCover(pictureId);
        return JsonResult.ok();
    }

    /**
     * 设置图片为非封面
     * @param pictureId 需要设置的图片id
     * @return JsonResult
     */
    @ApiOperation("设置图片为非封面")
    @ApiOperationSupport(order = 504)
    @ApiImplicitParam(name = "pictureId",value = "非封面的图片id",required = true,dataType = "long")
    @PostMapping("/{pictureId:[0-9]+}/isNotCover")
    public JsonResult<Void> isNotCover(@Range(min = 1,message = "设置失败,该图片id无效!")
                                   @PathVariable Long pictureId){
        log.debug("开始将id为{}的图片设置为非封面!",pictureId);
        pictureService.setNoCover(pictureId);
        return JsonResult.ok();
    }
}
