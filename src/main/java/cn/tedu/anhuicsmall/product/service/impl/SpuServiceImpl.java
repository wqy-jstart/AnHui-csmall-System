package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.*;
import cn.tedu.anhuicsmall.product.pojo.dto.SpuAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.SpuUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.*;
import cn.tedu.anhuicsmall.product.pojo.vo.SpuIndexListVO;
import cn.tedu.anhuicsmall.product.service.ISpuService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 商品Spu的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements ISpuService {

    public SpuServiceImpl() {
        log.debug("创建业务层接口实现类:SpuServiceImpl");
    }

    // 注入Spu的持久层接口
    @Autowired
    private SpuMapper spuMapper;

    // 注入品牌的持久层接口
    @Autowired
    private BrandMapper brandMapper;

    // 注入分类的持久层接口
    @Autowired
    private CategoryMapper categoryMapper;

    // 注入属性模板的持久层接口
    @Autowired
    private AttributeTemplateMapper attributeTemplateMapper;

    /**
     * 处理添加Spu的数据
     *
     * @param spuAddNewDTO 添加的spu数据
     */
    @Override
    public void insert(SpuAddNewDTO spuAddNewDTO) {
        log.debug("开始处理添加Spu的业务,参数:{}", spuAddNewDTO);
        log.debug("开始验证品牌是否存在...");
        Brand brand = brandMapper.selectById(spuAddNewDTO.getBrandId());
        if (brand == null) {
            String message = "添加失败,该Spu的品牌不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        log.debug("开始验证分类是否存在...");

        Category category = categoryMapper.selectById(spuAddNewDTO.getCategoryId());
        if (category == null) {
            String message = "添加失败,该Spu的分类不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        log.debug("开始验证属性模板是否存在...");

        AttributeTemplate attributeTemplate = attributeTemplateMapper.selectById(spuAddNewDTO.getAttributeTemplateId());
        if (attributeTemplate == null) {
            String message = "添加失败,该Spu的属性模板不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        log.debug("即将插入Spu数据...");
        Spu spu = new Spu();
        BeanUtils.copyProperties(spuAddNewDTO, spu);
        log.debug("开始插入Spu数据...参数:{}", spu);
        int rows = spuMapper.insert(spu);
        if (rows > 1) {
            String message = "添加失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    /**
     * 处理根据id删除Spu数据的功能
     *
     * @param spuId 要删除的spuId
     */
    @Override
    public void deleteById(Long spuId) {
        log.debug("开始处理删除id为{}的Spu数据", spuId);
        Spu querySpu = spuMapper.selectById(spuId);
        if (querySpu == null) {
            String message = "删除失败,该Spu数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        log.debug("开始执行删除Spu的功能...");
        int row = spuMapper.deleteById(spuId);
        if (row > 1) {
            String message = "删除失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    /**
     * 根据id修改Spu数据
     *
     * @param spuUpdateDTO 修改的数据
     */
    @Override
    public void update(SpuUpdateDTO spuUpdateDTO) {
        log.debug("开始处理修改id为{}的Spu数据", spuUpdateDTO.getId());
        Spu querySpu = spuMapper.selectById(spuUpdateDTO.getId());
        if (querySpu == null) {
            String message = "删除失败,该Spu数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        log.debug("即将向Spu表中修改数据...");
        Spu spu = new Spu();
        BeanUtils.copyProperties(spuUpdateDTO, spu);
        log.debug("开始向Spu表中修改数据...参数:{}", spu);
        int rows = spuMapper.updateById(spu);
        if (rows > 1) {
            String message = "修改失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    /**
     * 根据id查询Spu数据
     *
     * @param spuId spuId
     * @return 返回实体类
     */
    @Override
    public Spu selectById(Long spuId) {
        log.debug("开始处理查询id为{}的Spu数据", spuId);
        Spu querySpu = spuMapper.selectById(spuId);
        if (querySpu == null) {
            String message = "删除失败,该Spu数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return querySpu;
    }

    /**
     * 处理返回主页Spu列表的功能
     * @return 返回主页列表
     */
    @Override
    public List<SpuIndexListVO> selectIndexSpu() {
        log.debug("开始处理查询主页Spu列表信息的功能,无参!");
        return spuMapper.selectIndexSpu();
    }

    /**
     * 查询未上架的Spu列表
     *
     * @return 返回列表
     */
    @Override
    public List<Object> selectByNotIsP() {
        return updateByPC(0, null);
    }

    /**
     * 查询既审核又上架的Spu列表
     *
     * @return 返回列表
     */
    @Override
    public List<Object> selectByIsPC() {
        return updateByPC(1, 1);
    }

    /**
     * 查询根据销量排序的Spu主页列表信息
     * @return 返回列表信息
     */
    @Override
    public List<SpuIndexListVO> selectSortByTitle() {
        log.debug("开始处理根据商品标题降序的功能,无参!");
        return spuMapper.selectSortByTitle();
    }

    /**
     * 处理上架的功能
     *
     * @param id 要上架的spuId
     */
    @Override
    public void setPublish(Long id) {
        updatePublishById(id, 1);
    }

    /**
     * 处理下架的功能
     *
     * @param id 要下架的spuId
     */
    @Override
    public void setNotPublish(Long id) {
        updatePublishById(id, 0);
    }

    /**
     * 推荐的SpuId
     *
     * @param id 要推荐的SpuId
     */
    @Override
    public void setRecommend(Long id) {
        updateRecommendById(id, 1);
    }

    /**
     * 不推荐的SpuId
     *
     * @param id 不推荐的SpId
     */
    @Override
    public void setNotRecommend(Long id) {
        updateRecommendById(id, 0);
    }

    /**
     * 处理审核的功能
     *
     * @param id 要审核的spuId
     */
    @Override
    public void setCheck(Long id) {
        updateCheckById(id, 1);
    }

    /**
     * 处理未审核的功能
     *
     * @param id 未审核的spuId
     */
    @Override
    public void setNotCheck(Long id) {
        updateCheckById(id, 0);
    }

    /**
     * 该方法处理根据是否上架和是否审核两种不同的情况来返回对应的Spu列表的功能
     *
     * @param isPublished 是否上架
     * @param isChecked   是否审核
     * @return 返回列表数据
     */
    private List<Object> updateByPC(Integer isPublished, Integer isChecked) {
        if (isPublished == 0) {// 如果是第一种情况(未上架)
            QueryWrapper<Spu> wrapper = new QueryWrapper<>();
            wrapper.eq("is_published", 0);
            return spuMapper.selectObjs(wrapper);
        } else {
            QueryWrapper<Spu> wrapper = new QueryWrapper<>();
            wrapper.eq("is_published", isPublished);
            wrapper.eq("is_checked", isChecked);
            return spuMapper.selectObjs(wrapper);
        }
    }

    /**
     * 处理是否上架的业务逻辑
     *
     * @param spuId   SpuId
     * @param publish 1=上架;0=下架
     */
    private void updatePublishById(Long spuId, Integer publish) {
        String[] tips = {"下架", "上架"};
        log.debug("开始处理[{}Spu]的业务,id参数为{}", tips[publish], spuId);
        // 根据id查询分类详情
        Spu querySpu = spuMapper.selectById(spuId);
        if (querySpu == null) {
            String message = tips[publish] + "Spu失败,尝试访问的数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        // 判断查询结果中的publish与方法参数publish是否相同
        if (publish.equals(querySpu.getIsPublished())) {
            String message = tips[publish] + "Spu失败，分类已经处于" + tips[publish] + "状态！";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        if (publish == 1) {
            if (querySpu.getIsChecked() == 0) {
                String message = tips[publish] + "Spu失败,未经审核,商品不允许启用上架!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }
        // 创建Spu对象,并封装id和publish这2个属性的值,并进行修改
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setIsPublished(publish);
        int rows = spuMapper.updateById(spu);
        if (rows != 1) {
            String message = tips[publish] + "Spu失败，服务器忙，请稍后再次尝试！";
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        log.debug("修改成功!");
    }

    /**
     * 处理是否推荐的业务逻辑
     *
     * @param spuId     推荐的SpuId
     * @param recommend 1=推荐;0=不推荐
     */
    private void updateRecommendById(Long spuId, Integer recommend) {
        String[] tips = {"推荐", "不推荐"};
        log.debug("开始处理[{}Spu]的业务,id参数为{}", tips[recommend], spuId);
        // 根据id查询分类详情
        Spu querySpu = spuMapper.selectById(spuId);
        if (querySpu == null) {
            String message = tips[recommend] + "Spu失败,尝试访问的数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        // 判断查询结果中的check与方法参数check是否相同
        if (recommend.equals(querySpu.getIsRecommend())) {
            String message = tips[recommend] + "Spu失败，分类已经处于" + tips[recommend] + "状态！";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        // 创建Spu对象,并封装id和publish这2个属性的值,并进行修改
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setIsRecommend(recommend);
        int rows = spuMapper.updateById(spu);
        if (rows != 1) {
            String message = tips[recommend] + "Spu失败，服务器忙，请稍后再次尝试！";
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        log.debug("修改成功!");
    }

    /**
     * 处理是否审核的业务逻辑
     *
     * @param spuId spuId
     * @param check 1=审核;0=未审核
     */
    private void updateCheckById(Long spuId, Integer check) {
        String[] tips = {"已审核", "未审核"};
        log.debug("开始处理[{}Spu]的业务,id参数为{}", tips[check], spuId);
        // 根据id查询分类详情
        Spu querySpu = spuMapper.selectById(spuId);
        if (querySpu == null) {
            String message = tips[check] + "Spu失败,尝试访问的数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        // 判断查询结果中的check与方法参数check是否相同
        if (check.equals(querySpu.getIsChecked())) {
            String message = tips[check] + "Spu失败，分类已经处于" + tips[check] + "状态！";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        if (check == 0) { // 如果要设置为未审核状态
            if (querySpu.getIsPublished() == 1) { // 上架状态
                String message = tips[check] + "Spu失败,未经审核,商品不允许启用上架!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }
        // 创建Spu对象,并封装id和publish这2个属性的值,并进行修改
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setIsChecked(check);
        int rows = spuMapper.updateById(spu);
        if (rows != 1) {
            String message = tips[check] + "Spu失败，服务器忙，请稍后再次尝试！";
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        log.debug("修改成功!");
    }
}
