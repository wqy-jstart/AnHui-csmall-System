package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.BrandAndCategoryMapper;
import cn.tedu.anhuicsmall.product.mapper.BrandMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.BrandAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.BrandUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Banner;
import cn.tedu.anhuicsmall.product.pojo.entity.Brand;
import cn.tedu.anhuicsmall.product.pojo.entity.BrandAndCategory;
import cn.tedu.anhuicsmall.product.repo.IBrandRedisRepository;
import cn.tedu.anhuicsmall.product.service.IBrandService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 品牌的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    public BrandServiceImpl() {
        log.debug("创建业务层接口实现类:BrandServiceImpl");
    }

    // 注入品牌的持久层接口
    @Autowired
    private BrandMapper brandMapper;

    // 注入品牌与类别的持久层接口
    @Autowired
    private BrandAndCategoryMapper brandAndCategoryMapper;

    // 注入品牌的Redis缓存接口
    @Autowired
    private IBrandRedisRepository brandRedisRepository;

    /**
     * 处理添加品牌数据的业务
     *
     * @param brandAddNewDTO 注册需要的品牌信息
     */
    @Override
    public void insert(BrandAddNewDTO brandAddNewDTO) {
        log.debug("处理添加品牌的业务,参数:{}", brandAddNewDTO);
        // 判断添加品牌的名称是否被占用
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        wrapper.eq("name", brandAddNewDTO.getName());
        Brand queryBrand = brandMapper.selectOne(wrapper);
        if (queryBrand != null) {
            String message = "添加失败,该品牌名称已经被占用!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        log.debug("即将插入品牌数据...");
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandAddNewDTO, brand);
        int rows = brandMapper.insert(brand);
        if (rows > 1) {
            String message = "添加失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    /**
     * 根据id删除品牌数据
     *
     * @param brandId 要删除的品牌id
     */
    @Override
    public void deleteById(Long brandId) {
        log.debug("开始处理删除id为{}的品牌数据", brandId);
        Brand queryBrand = brandMapper.selectById(brandId);
        if (queryBrand == null) {
            String message = "删除失败,该品牌数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 判断品牌类别关联表中是否含有当前删除的品牌id
        QueryWrapper<BrandAndCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("brand_id", brandId);
        BrandAndCategory queryBrandAndCategory = brandAndCategoryMapper.selectOne(wrapper);
        if (queryBrandAndCategory != null) {
            String message = "删除失败,该品牌id存在与类别的关联数据!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        log.debug("即将删除id为{}的品牌数据", brandId);
        int rows = brandMapper.deleteById(brandId);
        if (rows > 1) {
            String message = "删除失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    /**
     * 根据id修改品牌数据
     *
     * @param brandUpdateDTO 品牌修改的信息
     */
    @Override
    public void update(BrandUpdateDTO brandUpdateDTO) {
        log.debug("开始处理修改id为{}的品牌数据", brandUpdateDTO.getId());
        Brand queryBrand = brandMapper.selectById(brandUpdateDTO.getId());
        if (queryBrand == null) {
            String message = "修改失败,该品牌数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 判断出该id以外的品牌是否与修改的品牌名称相同
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        wrapper.ne("id", brandUpdateDTO.getId());
        wrapper.eq("name", brandUpdateDTO.getName());
        Brand brandToName = brandMapper.selectOne(wrapper);
        if (brandToName != null) {
            String message = "修改失败,该品牌名称已经被占用!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        log.debug("即将向数据库中修改品牌数据...");
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandUpdateDTO, brand);
        log.debug("开始向数据库中修改品牌数据,参数:{}", brand);
        int rows = brandMapper.updateById(brand);
        if (rows > 1) {
            String message = "修改失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    /**
     * 根据id查询品牌数据
     * @param brandId 品牌id
     * @return 返回品牌实体类
     */
    @Override
    public Brand selectById(Long brandId) {
        log.debug("开始处理查询id为{}的品牌详情",brandId);
        log.debug("将从Redis中获取相关数据...");
        Brand brand = brandRedisRepository.get(brandId);

        if (brand != null){
            log.debug("命中缓存,即将返回:{}",brand);
            return brand;
        }

        log.debug("未命中缓存,即将向数据库中查询数据...");
        brand = brandMapper.selectById(brandId);
        if (brand == null) {
            String message = "查询失败,该品牌数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        log.debug("从数据库中查询到有效结果,将查询结果存入缓存中...");
        brandRedisRepository.save(brand);
        log.debug("开始返回结果!");
        return brand;
    }

    /**
     * 处理查询品牌列表的功能
     * @return 返回列表信息
     */
    @Override
    public List<Brand> selectList() {
        log.debug("开始处理查询品牌列表的功能");
        return brandRedisRepository.list();
    }

    /**
     * 处理启用品牌
     *
     * @param id 要启用的品牌id
     */
    @Override
    public void setEnable(Long id) {
        updateEnableById(id, 1);
    }

    /**
     * 处理禁用品牌
     *
     * @param id 要禁用的品牌id
     */
    @Override
    public void setDisable(Long id) {
        updateEnableById(id, 0);
    }

    /**
     * 手动重建Redis缓存的方法
     */
    @Override
    public void rebuildCache() {
        log.debug("准备删除Redis缓存中的品牌数据...");
        brandRedisRepository.deleteAll();// 清除缓存中的数据,防止缓存堆积过多,显示的列表数据冗余
        log.debug("删除Redis缓存中的品牌数据,完成!");

        log.debug("准备从数据库中读取品牌列表...");
        List<Brand> list = brandMapper.selectList(null); // 利用Mapper查询列表放到List中
        log.debug("从数据库中读取品牌列表，完成！");

        log.debug("准备将品牌列表写入到Redis缓存...");
        brandRedisRepository.save(list);// 利用品牌的Redis接口调用save转入要保存的列表,加载到缓存中
        log.debug("将品牌列表写入到Redis缓存，完成！");

        log.debug("准备将各品牌详情写入Redis缓存...");
        for (Brand brand : list) {// 遍历拿到的品牌列表
            Long id = brand.getId();// 获取遍历的每个品牌列表的id
            Brand brandStandardVO = brandMapper.selectById(id);// 利用拿到的id来查询对应的品牌详情
            brandRedisRepository.save(brandStandardVO);// 将每一个品牌详情放到Redis缓存中
        }
        log.debug("将各品牌详情写入到Redis缓存中,完成!");
    }

    /**
     * 处理是否启用的业务逻辑
     *
     * @param brandId 品牌id
     * @param enable     1=启用;0=禁用
     */
    private void updateEnableById(Long brandId, Integer enable) {
        String[] tips = {"禁用", "启用"};
        log.debug("开始处理[{}品牌]的业务,id参数为{}", tips[enable], brandId);
        // 根据id查询品牌详情
        Brand queryBrand = brandMapper.selectById(brandId);
        if (queryBrand == null) {
            String message = tips[enable] + "品牌失败,尝试访问的数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        // 判断查询结果中的enable与方法参数enable是否相同
        if (enable.equals(queryBrand.getEnable())) {
            String message = tips[enable] + "品牌失败，品牌已经处于" + tips[enable] + "状态！";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        // 创建brand对象,并封装id和enable这2个属性的值,并进行修改
        Brand brand = new Brand();
        brand.setId(brandId);
        brand.setEnable(enable);
        int rows = brandMapper.updateById(brand);
        if (rows != 1) {
            String message = tips[enable] + "品牌失败，服务器忙，请稍后再次尝试！";
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        log.debug("修改成功!");
    }

}
