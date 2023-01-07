package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.BrandAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.BrandUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 品牌的业务层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface IBrandService extends IService<Brand> {

    /**
     * 处理添加品牌的功能
     * @param brandAddNewDTO 添加需要的品牌信息
     */
    void insert(BrandAddNewDTO brandAddNewDTO);

    /**
     * 处理根据id删除品牌的功能
     * @param brandId 要删除的品牌id
     */
    void deleteById(Long brandId);

    /**
     * 根据品牌id修改品牌
     * @param brandUpdateDTO 品牌修改的信息
     */
    void update(BrandUpdateDTO brandUpdateDTO);

    /**
     * 根据id查询品牌详情
     * @param brandId 品牌id
     * @return 返回品牌详情
     */
    Brand selectById(Long brandId);

    /**
     * 处理查询后台品牌列表的功能
     * @return 品牌列表集合
     */
    List<Brand> selectList();

    /**
     * 处理启用品牌的功能
     * @param id 要启用的品牌id
     */
    void setEnable(Long id);

    /**
     * 处理禁用品牌的功能
     * @param id 要禁用的品牌id
     */
    void setDisable(Long id);
}
