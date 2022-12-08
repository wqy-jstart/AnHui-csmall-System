package cn.tedu.anhuicsmall.product.controller;

import cn.tedu.anhuicsmall.product.pojo.dto.CategoryAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.CategoryUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Category;
import cn.tedu.anhuicsmall.product.service.ICategoryService;
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
 * 分类的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "07.分类管理模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/categories")
public class CategoryController {

    public CategoryController(){
        log.debug("创建控制器类:CategoryController");
    }

    // 注入分类的业务层接口
    @Autowired
    private ICategoryService categoryService;

    /**
     * 处理添加分类的请求
     * @param categoryAddNewDTO 添加的分类信息
     * @return 返回结果集
     */
    // http://localhost:9900/categories/insert
    @ApiOperation("添加分类")
    @ApiOperationSupport(order = 100)
    @PostMapping("/insert")
    public JsonResult<Void> insert(@Valid CategoryAddNewDTO categoryAddNewDTO){
        log.debug("开始处理添加分类的请求,参数:{}",categoryAddNewDTO);
        categoryService.insert(categoryAddNewDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id删除分类信息
     * @param categoryId 要删除的分类id
     * @return 返回结果集
     */
    // http://localhost:9900/categories/categoryId/delete
    @ApiOperation("根据id删除分类")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "categoryId",value = "要删除的类别id",required = true,dataType = "long")
    @PostMapping("/{categoryId:[0-9]+}/deleteById")
    public JsonResult<Void> deleteById(@Range(min = 1,message = "删除失败,该类别id无效!")
                                           @PathVariable Long categoryId) {
        log.debug("开始处理[根据id删除类别]的请求,参数为{}",categoryId);
        categoryService.deleteById(categoryId);
        return JsonResult.ok();
    }

    /**
     * 根据id修改分类信息
     * @param categoryUpdateDTO 修改的分类数据
     * @return 返回结果集
     */
    // http://localhost:9900/categories/update
    @ApiOperation("根据id修改分类数据")
    @ApiOperationSupport(order = 300)
    @PostMapping("/update")
    public JsonResult<Void> update( CategoryUpdateDTO categoryUpdateDTO){
        log.debug("根据分类id{}修改分类信息",categoryUpdateDTO.getId());
        categoryService.update(categoryUpdateDTO);
        return JsonResult.ok();
    }

    /**
     * 根据id查询类别详情信息
     * @param categoryId 要查询的类别信息
     * @return 返回结果集
     */
    // http://localhost:9900/categories/categoryId/selectById
    @ApiOperation("根据id查询类别详情")
    @ApiOperationSupport(order = 500)
    @ApiImplicitParam(name = "categoryId",value = "类别id",required = true,dataType = "long")
    @GetMapping("/{categoryId:[0-9]+}/selectById")
    public JsonResult<Category> selectById(@Range(min = 1,message = "查询失败,该id无效!")@PathVariable Long categoryId){
        log.debug("开始处理根据id:{}查询类别的请求",categoryId);
        Category category = categoryService.selectById(categoryId);
        return JsonResult.ok(category);
    }

    /**
     * 处理查询分类列表的功能
     * @return 返回结果集
     */
    @ApiOperation("查询分类列表")
    @ApiOperationSupport(order = 501)
    @GetMapping("")
    // http://localhost:9900/categories/
    public JsonResult<List<Object>> list(){
        log.debug("开始处理查询分类列表的请求");
        List<Object> list = categoryService.selectList();
        return JsonResult.ok(list);
    }

    /**
     * 根据父级id查询子级类别列表
     * @param parentId 父级id
     * @return 返回子级列表信息
     */
    // http://localhost:9900/categories/parentId/listByParent
    @ApiOperation("根据父级类别查询子级类别")
    @ApiOperationSupport(order = 502)
    @GetMapping("/listByParent")
    public JsonResult<List<Object>> listByParentId(Long parentId){
        log.debug("开始处理[根据父级类别查询自己类别]的请求!");
        List<Object> list = categoryService.listByParentId(parentId);
        return JsonResult.ok(list);
    }

    /**
     * 启用分类
     * @param categoryId 需要启用的分类id
     * @return JsonResult
     */
    // http://localhost:9900/categories/categoryId/enable
    @ApiOperation("启用分类")
    @ApiOperationSupport(order = 503)
    @ApiImplicitParam(name = "categoryId",value = "启用的分类id",required = true,dataType = "long")
    @PostMapping("/{categoryId:[0-9]+}/enable")
    public JsonResult<Void> enable(@Range(min = 1,message = "启用分类失败,该id无效!")
                                   @PathVariable Long categoryId){
        log.debug("开始启用分类!");
        categoryService.setEnable(categoryId);
        return JsonResult.ok();
    }

    /**
     * 禁用分类
     * @param categoryId 需要禁用的类别id
     * @return JsonResult
     */
    // http://localhost:9900/categories/categoryId/disable
    @ApiOperation("禁用分类")
    @ApiOperationSupport(order = 504)
    @ApiImplicitParam(name = "categoryId",value = "禁用的分类id",required = true,dataType = "long")
    @PostMapping("/{categoryId:[0-9]+}/disable")
    public JsonResult<Void> disable(@Range(min = 1,message = "禁用分类失败,该id无效!")
                                    @PathVariable Long categoryId){
        log.debug("开始禁用分类!");
        categoryService.setDisable(categoryId);
        return JsonResult.ok();
    }

    /**
     * 显示分类
     * @param categoryId 需要显示的分类id
     * @return JsonResult
     */
    // http://localhost:9900/categories/categoryId/display
    @ApiOperation("显示分类")
    @ApiOperationSupport(order = 505)
    @ApiImplicitParam(name = "categoryId",value = "显示的分类id",required = true,dataType = "long")
    @PostMapping("/{categoryId:[0-9]+}/display")
    public JsonResult<Void> display(@Range(min = 1,message = "显示分类失败,该id无效!")
                                    @PathVariable Long categoryId){
        log.debug("开始显示分类!");
        categoryService.setDisplay(categoryId);
        return JsonResult.ok();
    }

    /**
     * 隐藏分类
     * @param categoryId 需要隐藏的分类id
     * @return JsonResult
     */
    // http://localhost:9900/categories/categoryId/hidden
    @ApiOperation("隐藏分类")
    @ApiOperationSupport(order = 506)
    @ApiImplicitParam(name = "categoryId",value = "隐藏的分类id",required = true,dataType = "long")
    @PostMapping("/{categoryId:[0-9]+}/hidden")
    public JsonResult<Void> hidden(@Range(min = 1,message = "隐藏分类失败,该id无效!")
                                   @PathVariable Long categoryId){
        log.debug("开始隐藏分类!");
        categoryService.setHidden(categoryId);
        return JsonResult.ok();
    }
}
