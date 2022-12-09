package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.BannerAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.BannerUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.BrandAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.BrandUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Banner;
import cn.tedu.anhuicsmall.product.pojo.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 轮播图的业务层接口类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface IBannerService extends IService<Banner> {

    /**
     * 处理添加轮播图的功能
     * @param bannerAddNewDTO 添加需要的轮播图信息
     */
    void insert(BannerAddNewDTO bannerAddNewDTO);

    /**
     * 处理根据id删除轮播图的功能
     * @param bannerId 要删除的轮播图id
     */
    void deleteById(Long bannerId);

    /**
     * 根据轮播图id修改轮播图
     * @param brandUpdateDTO 轮播图修改的信息
     */
    void update(BannerUpdateDTO brandUpdateDTO);

    /**
     * 根据id查询轮播图详情
     * @param bannerId 轮播图id
     * @return 返回轮播图详情
     */
    Banner selectById(Long bannerId);

    /**
     * 查询启用的轮播图列表
     * @return 返回列表集合
     */
    List<Object> selectListByEnable();

    /**
     * 处理查询后台轮播图列表的功能
     * @return 轮播图列表集合
     */
    List<Object> selectList();

    /**
     * 处理启用轮播图的功能
     * @param id 要启用的轮播图id
     */
    void setEnable(Long id);

    /**
     * 处理禁用轮播图的功能
     * @param id 要禁用的轮播图id
     */
    void setDisable(Long id);
}
