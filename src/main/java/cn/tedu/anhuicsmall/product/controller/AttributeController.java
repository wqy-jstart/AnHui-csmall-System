package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.AttributeAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AttributeUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Attribute;
import cn.tedu.anhuicsmall.product.service.IAttributeService;
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
 * 属性的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "04.属性管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/attributes")
public class AttributeController {

    public AttributeController(){
        log.debug("创建控制器对象:AttributeController");
    }

    // 注入属性的业务层接口
    @Autowired
    private IAttributeService attributeService;

    /**
     * 处理添加属性的功能
     * @param attributeAddNewDTO 添加的属性信息
     * @return 返回结果集
     */
    @ApiOperation("添加属性的功能")
    @ApiOperationSupport(order = 100)
    @PostMapping("insert")
    public JsonResult<Void> insert(@Valid AttributeAddNewDTO attributeAddNewDTO){
        log.debug("开始处理添加属性的请求,参数:{}",attributeAddNewDTO);
        attributeService.insert(attributeAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 处理根据id删除属性的功能
     * @param attributeId 要删除的属性id
     * @return 返回结果集
     */
    @ApiOperation("根据id删除属性")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "attributeId",value = "属性id",required = true,dataType = "long")
    @PostMapping("/{attributeId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@Range(min = 1,message = "删除失败,该属性id无效!")
                                       @PathVariable Long attributeId){
        log.debug("开始处理删除id为{}的属性请求",attributeId);
        attributeService.deleteById(attributeId);
        return JsonResult.ok();
    }

    /**
     * 根据id修改属性信息
     * @param attributeUpdateDTO 要修改的属性信息
     * @return 返回结果集
     */
    @ApiOperation("根据id修改属性信息")
    @ApiOperationSupport(order = 300)
    @PostMapping("update")
    public JsonResult<Void> update(AttributeUpdateDTO attributeUpdateDTO){
        log.debug("开始处理修改id为{}的属性信息的请求",attributeUpdateDTO.getId());
        attributeService.update(attributeUpdateDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id查询属性信息
     * @param attributeId 要查询的属性id
     * @return 返回结果集
     */
    @ApiOperation("根据id查询属性信息")
    @ApiOperationSupport(order = 500)
    @ApiImplicitParam(name = "attributeId",value = "属性id",required = true,dataType = "long")
    @GetMapping("/{attributeId:[0-9]+}/selectById")
    public JsonResult<Attribute> selectById(@Range(min = 1,message = "查询失败,该属性id为无效!")
                                            @PathVariable Long attributeId){
        log.debug("开始处理查询id为{}的属性详情",attributeId);
        Attribute attribute = attributeService.selectById(attributeId);
        return JsonResult.ok(attribute);
    }

    /**
     * 处理查询属性列表的功能
     * @return 返回列表集合
     */
    @ApiOperation("根据模板id查询属性列表")
    @ApiOperationSupport(order = 501)
    @ApiImplicitParam(name = "templateId",value = "模板id",required = true,dataType = "long")
    @GetMapping("/{templateId:[0-9]+}/listByTemplate")
    public JsonResult<List<Object>> selectListToTemplateId(@Range(min = 1,message = "查询失败,该模板id为无效!")
                                                           @PathVariable Long templateId){
        log.debug("开始处理查询属性列表的请求,无参!");
        List<Object> objects = attributeService.selectListToTemplateId(templateId);
        return JsonResult.ok(objects);
    }

    /**
     * 处理查询属性列表的功能
     * @return 返回列表集合
     */
    @ApiOperation("查询属性列表")
    @ApiOperationSupport(order = 502)
    @GetMapping("")
    public JsonResult<List<Object>> selectList(){
        log.debug("开始处理查询属性列表的请求,无参!");
        List<Object> objects = attributeService.selectList();
        return JsonResult.ok(objects);
    }
}
