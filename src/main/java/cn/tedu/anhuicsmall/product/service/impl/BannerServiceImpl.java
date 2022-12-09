package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.BannerMapper;
import cn.tedu.anhuicsmall.product.mapper.SpuMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.BannerAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.BannerUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Banner;
import cn.tedu.anhuicsmall.product.pojo.entity.Brand;
import cn.tedu.anhuicsmall.product.pojo.entity.Spu;
import cn.tedu.anhuicsmall.product.service.IBannerService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    public BannerServiceImpl(){
        log.debug("创建业务层接口实现类:BannerServiceImpl");
    }

    // 注入轮播图的持久层接口
    @Autowired
    private BannerMapper bannerMapper;

    // 注入商品SPU的持久层接口
    @Autowired
    private SpuMapper spuMapper;

    /**
     * 添加轮播图数据
     * @param bannerAddNewDTO 添加需要的轮播图信息
     */
    @Override
    public void insert(BannerAddNewDTO bannerAddNewDTO) {
        log.debug("开始处理添加轮播图的功能,参数:{}",bannerAddNewDTO);
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.eq("url",bannerAddNewDTO.getUrl());
        Banner queryBanner = bannerMapper.selectOne(wrapper);
        if (queryBanner != null){
            String message = "添加失败,该轮播图已存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
        }

        QueryWrapper<Spu> wrapperToSpu = new QueryWrapper<>();
        wrapperToSpu.eq("id",bannerAddNewDTO.getSpuId());
        Integer count = spuMapper.selectCount(wrapperToSpu);
        if(count == 0){
            String message = "添加失败,该关联的spu数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }

        QueryWrapper<Banner> wrapperToSpuId = new QueryWrapper<>();
        wrapperToSpuId.eq("spu_id",bannerAddNewDTO.getSpuId());
        Integer count1 = bannerMapper.selectCount(wrapperToSpuId);
        if (count1 !=0){
            String message = "添加失败,该spu数据已经被关联!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }

        log.debug("即将添加轮播图数据...");
        Banner banner = new Banner();
        BeanUtils.copyProperties(bannerAddNewDTO,banner);
        log.debug("开始添加轮播图数据,参数:{}",banner);
        int rows = bannerMapper.insert(banner);
        if (rows>1){
            String message = "添加失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }
    }

    /**
     * 根据id删除轮播图数据
     * @param bannerId 要删除的轮播图id
     */
    @Override
    public void deleteById(Long bannerId) {
        log.debug("开始处理删除id为{}的轮播图数据",bannerId);
        Banner queryBanner = bannerMapper.selectById(bannerId);
        if (queryBanner == null){
            String message = "删除失败,该轮播图不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }

        Spu querySpu = spuMapper.selectById(queryBanner.getSpuId());
        if (querySpu != null){
            String message = "删除失败,该轮播图含有关联的SPU数据";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
        }

        log.debug("即将删除id为{}的轮播图数据",bannerId);
        int rows = bannerMapper.deleteById(bannerId);
        if (rows>1){
            String message = "删除失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE,message);
        }

    }

    /**
     * 根据id修改轮播图数据
     * @param bannerUpdateDTO 修改的轮播图信息
     */
    @Override
    public void update(BannerUpdateDTO bannerUpdateDTO) {
        log.debug("开始处理修改id为{}的轮播图数据",bannerUpdateDTO.getId());
        Banner queryBanner = bannerMapper.selectById(bannerUpdateDTO.getId());
        if (queryBanner == null){
            String message = "修改失败,该轮播图不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }

        // 判断除修改的spuId之外的轮播图是否重复出现spuId
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.eq("spu_id",bannerUpdateDTO.getSpuId());
        wrapper.ne("id",bannerUpdateDTO.getId());
        Integer count = bannerMapper.selectCount(wrapper);
        if (count!=0){
            String message = "修改失败,该spuId已经被其他轮播图占用";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
        }

        Spu querySpu = spuMapper.selectById(bannerUpdateDTO.getSpuId());
        if (querySpu == null){
            String message = "修改失败,修改的SpuId不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }

        log.debug("即将修改id为{}的轮播图数据",bannerUpdateDTO.getId());
        Banner banner = new Banner();
        BeanUtils.copyProperties(bannerUpdateDTO,banner);
        log.debug("开始修改id为{}的轮播图数据",bannerUpdateDTO.getId());
        int rows = bannerMapper.updateById(banner);
        if (rows>1){
            String message = "修改失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE,message);
        }
    }

    /**
     * 根据id查询轮播图列表
     * @param bannerId 轮播图id
     * @return 返回实体类
     */
    @Override
    public Banner selectById(Long bannerId) {
        log.debug("开始处理查询id为{}的轮播图信息",bannerId);
        Banner queryBanner = bannerMapper.selectById(bannerId);
        if (queryBanner == null){
            String message = "查询失败,该轮播图数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }
        return queryBanner;
    }

    /**
     * 查询启用的轮播图列表
     * @return 返回列表集合
     */
    @Override
    public List<Object> selectListByEnable() {
        log.debug("开始处理查询启用的轮播图列表数据,无参!");
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.eq("enable",1);
        return bannerMapper.selectObjs(wrapper);
    }

    /**
     * 处理查询轮播图列表的业务
     * @return 返回轮播图列表
     */
    @Override
    public List<Object> selectList() {
        log.debug("开始处理查询轮播图列表的信息,无参!");
        return bannerMapper.selectObjs(null);
    }

    /**
     * 启用轮播图
     * @param id 要启用的轮播图id
     */
    @Override
    public void setEnable(Long id) {
        updateEnableById(id,1);
    }

    /**
     * 禁用轮播图
     * @param id 要禁用的轮播图id
     */
    @Override
    public void setDisable(Long id) {
        updateEnableById(id,0);
    }

    /**
     * 处理是否启用的业务逻辑
     *
     * @param bannerId 轮播图id
     * @param enable     1=启用;0=禁用
     */
    private void updateEnableById(Long bannerId, Integer enable) {
        String[] tips = {"禁用", "启用"};
        log.debug("开始处理[{}轮播图]的业务,id参数为{}", tips[enable], bannerId);
        // 根据id查询品牌详情
        Banner queryBanner = bannerMapper.selectById(bannerId);
        if (queryBanner == null) {
            String message = tips[enable] + "轮播图失败,尝试访问的数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        // 判断查询结果中的enable与方法参数enable是否相同
        if (enable.equals(queryBanner.getEnable())) {
            String message = tips[enable] + "轮播图失败，轮播图已经处于" + tips[enable] + "状态！";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        if (enable == 1){ //如果要启用轮播图
            // 查询除此次启用的轮播图id外,其余轮播图启用的数量是否在4以内
            QueryWrapper<Banner> wrapper = new QueryWrapper<>();
            wrapper.ne("id",bannerId);
            wrapper.eq("enable",1);
            Integer count = bannerMapper.selectCount(wrapper);
            if (count >4){
                String message = tips[enable] + "轮播图失败,轮播图已达上限!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
            }
        }
        if (enable == 0){
            // 查询除此次启用的轮播图id外,其余轮播图启用的数量是否在4以内
            QueryWrapper<Banner> wrapper = new QueryWrapper<>();
            wrapper.ne("id",bannerId);
            wrapper.eq("enable",1);
            Integer count = bannerMapper.selectCount(wrapper);
            if (count == 0){
                String message = tips[enable] + "轮播图失败,这是最后一张轮播图!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
            }
        }

        // 创建brand对象,并封装id和enable这2个属性的值,并进行修改
        Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setEnable(enable);
        int rows = bannerMapper.updateById(banner);
        if (rows != 1) {
            String message = tips[enable] + "品牌失败，服务器忙，请稍后再次尝试！";
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        log.debug("修改成功!");
    }
}
