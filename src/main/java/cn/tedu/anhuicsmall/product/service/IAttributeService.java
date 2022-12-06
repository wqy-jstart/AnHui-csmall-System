package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.AlbumAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AlbumUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AttributeAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AttributeUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Album;
import cn.tedu.anhuicsmall.product.pojo.entity.Attribute;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 属性的业务层接口
 * 
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface IAttributeService extends IService<Attribute> {

    /**
     * 处理属性注册时添加属性的功能
     * @param attributeAddNewDTO 注册需要的属性信息
     */
    void insert(AttributeAddNewDTO attributeAddNewDTO);

    /**
     * 处理根据id删除属性的功能
     * @param attributeId 要删除的属性id
     */
    void deleteById(Long attributeId);

    /**
     * 根据属性id修改属性
     * @param attributeUpdateDTO 属性修改的信息
     */
    void update(AttributeUpdateDTO attributeUpdateDTO);

    /**
     * 根据id查询属性详情
     * @param attributeId 属性id
     * @return 返回属性详情
     */
    Attribute selectById(Long attributeId);

    /**
     * 处理查询后台属性列表的功能
     * @return 属性列表集合
     */
    List<Object> selectList();
}
