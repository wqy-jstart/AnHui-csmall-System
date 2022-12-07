package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.AttributeMapper;
import cn.tedu.anhuicsmall.product.mapper.AttributeTemplateMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.AttributeAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AttributeUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Attribute;
import cn.tedu.anhuicsmall.product.pojo.entity.AttributeTemplate;
import cn.tedu.anhuicsmall.product.service.IAttributeService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 属性的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class AttributeServiceImpl extends ServiceImpl<AttributeMapper, Attribute> implements IAttributeService {

    public AttributeServiceImpl() {
        log.debug("创建业务层接口实现类:AttributeServiceImpl");
    }

    // 注入属性的持久层接口
    @Autowired
    private AttributeMapper attributeMapper;

    // 注入属性模板的持久层接口
    @Autowired
    private AttributeTemplateMapper attributeTemplateMapper;

    /**
     * 处理添加属性的业务
     *
     * @param attributeAddNewDTO 注册需要的属性信息
     */
    @Override
    public void insert(AttributeAddNewDTO attributeAddNewDTO) {
        log.debug("开始处理添加属性的业务,参数:{}", attributeAddNewDTO);
        // 判断属性id是否存在
        AttributeTemplate queryAttributeTemplate = attributeTemplateMapper.selectById(attributeAddNewDTO.getTemplateId());
        if (queryAttributeTemplate == null) {
            String message = "添加失败,该属性对应的属性模板id不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        log.debug("即将处理插入属性的功能...");
        Attribute attribute = new Attribute();
        BeanUtils.copyProperties(attributeAddNewDTO, attribute);
        int rows = attributeMapper.insert(attribute);
        if (rows > 1) {
            String message = "添加失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    /**
     * 根据id删除属性的功能
     *
     * @param attributeId 要删除的属性id
     */
    @Override
    public void deleteById(Long attributeId) {
        log.debug("开始处理删除id为{}的属性信息功能", attributeId);
        Attribute queryAttribute = attributeMapper.selectById(attributeId);
        if (queryAttribute == null) {
            String message = "删除失败,该属性数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        int rows = attributeMapper.deleteById(attributeId);
        if (rows > 1) {
            String message = "删除失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    /**
     * 根据id修改属性的功能
     *
     * @param attributeUpdateDTO 属性修改的信息
     */
    @Override
    public void update(AttributeUpdateDTO attributeUpdateDTO) {
        log.debug("开始处理修改id为{}的属性功能", attributeUpdateDTO.getId());
        // 属性id是否存在
        log.debug("开始检查属性id是否存在...");
        Attribute queryAttribute = attributeMapper.selectById(attributeUpdateDTO.getId());
        if (queryAttribute == null) {
            String message = "修改失败,该属性数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        // 修改的模板id是否存在
        AttributeTemplate queryAttributeTemplate = attributeTemplateMapper.selectById(attributeUpdateDTO.getTemplateId());
        if (queryAttributeTemplate == null) {
            String message = "修改失败,该属性对应的属性模板id不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        log.debug("即将修改id为{}的属性信息...", attributeUpdateDTO.getId());
        Attribute attribute = new Attribute();
        BeanUtils.copyProperties(attributeUpdateDTO, attribute);
        int rows = attributeMapper.updateById(attribute);
        if (rows > 1) {
            String message = "修改失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    /**
     * 处理根据id查询属性信息的功能
     * @param attributeId 属性id
     * @return 属性实体类
     */
    @Override
    public Attribute selectById(Long attributeId) {
        log.debug("开始处理查询id为{}的属性信息功能",attributeId);
        // 属性id是否存在
        log.debug("开始检查属性id是否存在...");
        Attribute queryAttribute = attributeMapper.selectById(attributeId);
        if (queryAttribute == null) {
            String message = "查询失败,该属性数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return queryAttribute;
    }

    /**
     * 处理查询属性列表的功能
     * @return 属性列表集合
     */
    @Override
    public List<Object> selectList() {
        log.debug("开始处理查询属性列表信息功能,无参!");
        return attributeMapper.selectObjs(null);
    }
}
