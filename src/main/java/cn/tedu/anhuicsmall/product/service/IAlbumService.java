package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.AddressAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AddressUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AlbumAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AlbumUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Address;
import cn.tedu.anhuicsmall.product.pojo.entity.Album;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 相册的业务层接口类
 * 
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface IAlbumService extends IService<Album> {


    /**
     * 处理相册注册时添加相册的功能
     * @param albumAddNewDTO 注册需要的相册信息
     */
    void insert(AlbumAddNewDTO albumAddNewDTO);

    /**
     * 处理根据id删除相册的功能
     * @param albumId 要删除的相册id
     */
    void deleteById(Long albumId);

    /**
     * 根据相册id修改相册
     * @param albumUpdateDTO 相册修改的信息
     */
    void update(AlbumUpdateDTO albumUpdateDTO);

    /**
     * 根据id查询相册详情
     * @param albumId 相册id
     * @return 返回相册详情
     */
    Album selectById(Long albumId);

    /**
     * 处理查询后台相册列表的功能
     * @return 相册列表集合
     */
    List<Object> selectList();
}
