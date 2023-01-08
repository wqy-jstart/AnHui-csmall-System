package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.AttributeMapper;
import cn.tedu.anhuicsmall.product.mapper.AttributeTemplateMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.AttributeTemplateAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AttributeTemplateUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Attribute;
import cn.tedu.anhuicsmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.anhuicsmall.product.service.IAttributeTemplateService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 属性模板的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class AttributeTemplateServiceImpl extends ServiceImpl<AttributeTemplateMapper, AttributeTemplate> implements IAttributeTemplateService {

    public AttributeTemplateServiceImpl() {
        log.debug("创建业务层接口实现类：AttributeTemplateServiceImpl");
    }

    // 注入属性模板的持久层接口
    @Autowired
    private AttributeTemplateMapper attributeTemplateMapper;

    // 注入属性的持久层接口
    @Autowired
    private AttributeMapper attributeMapper;

    /**
     * 处理添加属性模板的业务
     *
     * @param attributeTemplateAddNewDTO 注册需要的属性模板信息
     */
    @Override
    public void insert(AttributeTemplateAddNewDTO attributeTemplateAddNewDTO) {
        log.debug("开始处理添加属性模板的业务，参数：{}", attributeTemplateAddNewDTO);
        // 检查属性模板的名称是否被占用
        QueryWrapper<AttributeTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("name", attributeTemplateAddNewDTO.getName());
        AttributeTemplate queryAttributeTemplate = attributeTemplateMapper.selectOne(wrapper);
        if (queryAttributeTemplate != null) {
            String message = "添加失败,该属性模板已经存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        log.debug("即将向属性模板表中插入数据...");
        AttributeTemplate attributeTemplate = new AttributeTemplate();
        BeanUtils.copyProperties(attributeTemplateAddNewDTO, attributeTemplate);
        log.debug("开始向属性模板表中插入数据...");
        int rows = attributeTemplateMapper.insert(attributeTemplate);
        if (rows > 1) {
            String message = "添加失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }

    }

    /**
     * 根据id删除属性模板的功能
     *
     * @param attributeTemplateId 要删除的属性模板id
     */
    @Override
    public void deleteById(Long attributeTemplateId) {
        log.debug("处理删除id为{}的属性模板业务", attributeTemplateId);
        AttributeTemplate queryAttributeTemplate = attributeTemplateMapper.selectById(attributeTemplateId);
        if (queryAttributeTemplate == null) {
            String message = "删除失败,该属性模板不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        log.debug("开始执行删除id为{}的属性模板信息", attributeTemplateId);
        int rowsToAttributeTemplate = attributeTemplateMapper.deleteById(attributeTemplateId);
        if (rowsToAttributeTemplate > 1) {
            String message = "删除失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }

        // 删除与该模板id关系的所有属性数据
        QueryWrapper<Attribute> wrapper = new QueryWrapper<>();
        wrapper.eq("template_id", attributeTemplateId);
        int rowsToAttribute = attributeMapper.delete(wrapper);
        if (rowsToAttribute < 1) { // 一个属性模板id对应的属性不止一个
            String message = "删除失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    /**
     * 根据id修改属性模板的信息
     *
     * @param attributeTemplateUpdateDTO 属性模板修改的信息
     */
    @Override
    public void update(AttributeTemplateUpdateDTO attributeTemplateUpdateDTO) {
        log.debug("开始处理修改id为{}的属性模板", attributeTemplateUpdateDTO.getId());
        AttributeTemplate queryAttributeTemplate = attributeTemplateMapper.selectById(attributeTemplateUpdateDTO.getId());
        if (queryAttributeTemplate == null) {
            String message = "修改失败,该属性模板不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 在修改过程中,属性模板的名称在除自身以外的前提下不能相同
        QueryWrapper<AttributeTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("name", attributeTemplateUpdateDTO.getName());
        wrapper.ne("id", attributeTemplateUpdateDTO.getId());
        AttributeTemplate attributeTemplate1 = attributeTemplateMapper.selectOne(wrapper);
        if (attributeTemplate1 !=null){
            String message = "修改失败,该名称已经被占用!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }

        log.debug("即将执行修改属性模板的业务...");
        AttributeTemplate attributeTemplate = new AttributeTemplate();
        BeanUtils.copyProperties(attributeTemplateUpdateDTO, attributeTemplate);
        log.debug("开始执行修改id为{}属性模板的业务", attributeTemplate.getId());
        int rows = attributeTemplateMapper.updateById(attributeTemplate);
        if (rows > 1) {
            String message = "修改失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    /**
     * 根据id查询属性模板的数据
     *
     * @param attributeTemplateId 属性模板id
     * @return 返回属性模板的实体类
     */
    @Override
    public AttributeTemplate selectById(Long attributeTemplateId) {
        log.debug("开始处理查询id为{}的属性模板详情", attributeTemplateId);
        AttributeTemplate queryAttributeTemplate = attributeTemplateMapper.selectById(attributeTemplateId);
        if (queryAttributeTemplate == null) {
            String message = "查询失败,该属性模板不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return queryAttributeTemplate;
    }

    /**
     * 查询属性模板列表的功能
     *
     * @return 返回列表集合
     */
    @Override
    public List<Object> selectList() {
        log.debug("开始处理查询属性模板列表的功能,无参!");
        return attributeTemplateMapper.selectObjs(null);
    }

    /**
     * 处理查询属性模板数量的功能
     * @return 返回属性模板的数量
     */
    @Override
    public Integer selectCount() {
        log.debug("开始处理查询属性模板数量的功能，无参！");
        return attributeTemplateMapper.selectCount(null);
    }

    /**
     * 处理分页查询属性模板的功能
     * @param current 当前页码
     * @param size 当前页数
     * @return 返回分页的列表数据
     */
    @Override
    public List<AttributeTemplate> selectToPage(Integer current, Integer size) {
        log.debug("开始处理分页查询属性模板列表的功能，当前页为:{},每页数量为:{}",current,size);
        Page<AttributeTemplate> page = new Page<>(current,size);
        IPage<AttributeTemplate> page1 = attributeTemplateMapper.selectPage(page, null);
        return page1.getRecords();
    }
}
