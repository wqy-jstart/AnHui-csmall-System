package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.CategoryAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.CategoryUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类别的业务层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface ICategoryService extends IService<Category> {

    /**
     * 添加分类信息
     * @param categoryAddNewDTO 封装了用户添加的类别信息
     */
    void insert(CategoryAddNewDTO categoryAddNewDTO);

    /**
     * 根据id删除分类信息
     * @param categoryId 分类id
     */
    void deleteById(Long categoryId);

    /**
     * 根据id修改分类信息
     * @param categoryUpdateDTO 分类信息
     */
    void update(CategoryUpdateDTO categoryUpdateDTO);

    /**
     * 根据id查询类别详情
     * @param categoryId 类别id
     * @return 返回类别实体类
     */
    Category selectById(Long categoryId);

    /**
     * 查询类别列表信息
     * @return 返回列表集合
     */
    List<Object> selectList();

    /**
     * 根据父级类别查询其自己类别列表
     * @param parentId 父级类别id
     * @return 返回查询的子级列表
     */
    List<Object> listByParentId(Long parentId);

    /**
     * 处理启用分类的功能
     * @param id 要启用的分类id
     */
    void setEnable(Long id);

    /**
     * 处理禁用分类的功能
     * @param id 要禁用的分类id
     */
    void setDisable(Long id);

    /**
     * 处理显示分类在导航栏中的功能
     * @param id 显示分类的id
     */
    void setDisplay(Long id);

    /**
     * 处理不显示分类在导航栏中的功能
     * @param id 隐藏的分类id
     */
    void setHidden(Long id);
}
