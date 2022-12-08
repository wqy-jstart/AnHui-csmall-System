package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.AttributeTemplateAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AttributeTemplateUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.anhuicsmall.product.service.IAttributeTemplateService;
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
 * 属性模板的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "05.属性模板管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/attributeTemplates")
public class AttributeTemplateController {

    public AttributeTemplateController(){
        log.debug("创建控制器类:AttributeTemplateController");
    }

    // 注入属性模板的业务层接口
    @Autowired
    private IAttributeTemplateService attributeTemplateService;

    /**
     * 处理添加属性模板的请求
     * @param attributeTemplateAddNewDTO 添加信息
     * @return 返回结果集
     */
    @ApiOperation("添加属性模板")
    @ApiOperationSupport(order = 100)
    @PostMapping("/insert")
    public JsonResult<Void> insert(@Valid AttributeTemplateAddNewDTO attributeTemplateAddNewDTO){
        log.debug("开始处理添加属性模板的请求,参数:{}",attributeTemplateAddNewDTO);
        attributeTemplateService.insert(attributeTemplateAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id删除属性模板的请求
     * @param attributeTemplateId 要删除的属性模板id
     * @return 返回结果集
     */
    @ApiOperation("根据id删除属性模板")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "attributeTemplateId",value = "属性模板id",required = true,dataType = "long")
    @PostMapping("/{attributeTemplateId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@Range(min = 1,message = "删除失败,该属性模板id无效!")
                                       @PathVariable Long attributeTemplateId){
        log.debug("开始处理删除id为{}的属性模板的请求",attributeTemplateId);
        attributeTemplateService.deleteById(attributeTemplateId);
        return JsonResult.ok();
    }

    /**
     * 根据id修改属性模板的请求
     * @param attributeTemplateUpdateDTO 修改的信息
     * @return 返回结果集
     */
    @ApiOperation("根据id修改属性模板")
    @ApiOperationSupport(order = 300)
    @PostMapping("/update")
    public JsonResult<Void> update(AttributeTemplateUpdateDTO attributeTemplateUpdateDTO){
        log.debug("开始处理修改id为{}的属性模板请求",attributeTemplateUpdateDTO.getId());
        attributeTemplateService.update(attributeTemplateUpdateDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id查询属性模板的信息
     * @param attributeTemplateId 查询的属性模板id
     * @return 返回结果集
     */
    @ApiOperation("根据id查询属性模板")
    @ApiOperationSupport(order = 500)
    @ApiImplicitParam(name = "attributeTemplateId",value = "属性模板id]",required = true,dataType = "long")
    @GetMapping("/{attributeTemplateId:[0-9]+}/selectById")
    public JsonResult<AttributeTemplate> selectById(@Range(min = 1,message = "查询失败,该属性模板id无效!")
                                                    @PathVariable Long attributeTemplateId){
        log.debug("开始处理查询id为{}的属性模板信息",attributeTemplateId);
        AttributeTemplate attributeTemplate = attributeTemplateService.selectById(attributeTemplateId);
        return JsonResult.ok(attributeTemplate);
    }

    /**
     * 查询属性模板列表的请求
     * @return 返回结果集
     */
    @ApiOperation("查询属性模板列表")
    @ApiOperationSupport(order = 501)
    @GetMapping("")
    public JsonResult<List<Object>> selectList(){
        log.debug("开始处理查询属性模板列表的请求,无参!");
        List<Object> objects = attributeTemplateService.selectList();
        return JsonResult.ok(objects);
    }
}
