package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.BrandAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.BrandUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.PictureAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.PictureUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Brand;
import cn.tedu.anhuicsmall.product.pojo.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 图片的业务层接口类
 * 
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface IPictureService extends IService<Picture> {

    /**
     * 处理添加图片的功能
     * @param pictureAddNewDTO 注册需要的图片信息
     */
    void insert(PictureAddNewDTO pictureAddNewDTO);

    /**
     * 处理根据id删除图片的功能
     * @param pictureId 要删除的图片id
     */
    void deleteById(Long pictureId);

    /**
     * 根据图片id修改图片
     * @param pictureUpdateDTO 图片修改的信息
     */
    void update(PictureUpdateDTO pictureUpdateDTO);

    /**
     * 根据id查询图片详情
     * @param pictureId 图片id
     * @return 返回图片详情
     */
    Picture selectById(Long pictureId);

    /**
     * 处理查询后台图片列表的功能
     * @return 图片列表集合
     */
    List<Object> selectList();

    /**
     * 根据相册查询图片列表
     * @param albumId 相册id
     * @return 列表集合
     */
    List<Object> selectListByAlbumId(Long albumId);

    /**
     * 处理封面图片的功能
     * @param id 要当封面的图片id
     */
    void setCover(Long id);

    /**
     * 处理非封面图片的功能
     * @param id 取消封面的图片id
     */
    void setNoCover(Long id);
}
