package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.AlbumAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AlbumUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Album;
import cn.tedu.anhuicsmall.product.service.IAlbumService;
import cn.tedu.anhuicsmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 相册控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "03.相册管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/albums")
public class AlbumController {

    public AlbumController() {
        log.debug("创建控制器类:AlbumController");
    }

    // 注入相册的业务层接口
    @Autowired
    private IAlbumService albumService;

    /**
     * 处理添加相册的请求
     */
    @ApiOperation("添加相册")
    @ApiOperationSupport(order = 100)
    @PostMapping("/insert")
    public JsonResult<Void> insert(@Valid AlbumAddNewDTO albumAddNewDTO) {
        log.debug("开始处理添加相册信息的请求,参数:{}", albumAddNewDTO);
        albumService.insert(albumAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 处理根据id删除相册信息的请求
     *
     * @param albumId 要删除的相册id
     * @return 返回删除的结果集
     */
    @ApiOperation("根据id删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "long")
    @PostMapping("/{albumId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@Range(min = 1, message = "删除失败,该相册id无效!")
                                       @PathVariable Long albumId) {
        log.debug("开始删除id为{}的相册信息", albumId);
        albumService.deleteById(albumId);
        return JsonResult.ok();
    }

    /**
     * 处理根据id修改相册信息的请求
     *
     * @param albumUpdateDTO 修改的相册信息
     * @return 返回结果集
     */
    @ApiOperation("根据id修改相册信息")
    @ApiOperationSupport(order = 300)
    @PostMapping("/update")
    public JsonResult<Void> update(AlbumUpdateDTO albumUpdateDTO) {
        log.debug("开始处理修改id为{}的相册信息", albumUpdateDTO.getId());
        albumService.update(albumUpdateDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id查询相册信息的请求
     *
     * @param albumId 相册id
     * @return 返回结果集
     */
    @ApiOperation("根据id查询相册详情")
    @ApiOperationSupport(order = 500)
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "long")
    @GetMapping("/{albumId:[0-9]+}/selectById")
    public JsonResult<Album> selectById(@Range(min = 1, message = "查询失败,该相册id无效!")
                                        @PathVariable Long albumId) {
        log.debug("开始处理查询id为{}的相册信息", albumId);
        Album album = albumService.selectById(albumId);
        return JsonResult.ok(album);
    }

    /**
     * 处理查询相册列表的请求
     *
     * @return 返回查询的结果集
     */
    @ApiOperation("查询相册列表")
    @ApiOperationSupport(order = 501)
    @GetMapping("")
    public JsonResult<List<Object>> selectList() {
        log.debug("开始处理查询相册列表的功能,无参!");
        List<Object> objects = albumService.selectList();
        return JsonResult.ok(objects);
    }
}
