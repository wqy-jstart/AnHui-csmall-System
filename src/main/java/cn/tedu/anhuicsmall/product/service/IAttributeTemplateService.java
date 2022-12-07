package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.AttributeTemplateAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AttributeTemplateUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.AttributeTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 属性模板模板的业务层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface IAttributeTemplateService extends IService<AttributeTemplate> {


    /**
     * 处理添加属性模板的功能
     * @param attributeTemplateAddNewDTO 注册需要的属性模板信息
     */
    void insert(AttributeTemplateAddNewDTO attributeTemplateAddNewDTO);

    /**
     * 处理根据id删除属性模板的功能
     * @param attributeTemplateId 要删除的属性模板id
     */
    void deleteById(Long attributeTemplateId);

    /**
     * 根据属性模板id修改属性模板
     * @param attributeTemplateUpdateDTO 属性模板修改的信息
     */
    void update(AttributeTemplateUpdateDTO attributeTemplateUpdateDTO);

    /**
     * 根据id查询属性模板详情
     * @param attributeTemplateId 属性模板id
     * @return 返回属性模板详情
     */
    AttributeTemplate selectById(Long attributeTemplateId);

    /**
     * 处理查询后台属性模板列表的功能
     * @return 属性模板列表集合
     */
    List<Object> selectList();
}
