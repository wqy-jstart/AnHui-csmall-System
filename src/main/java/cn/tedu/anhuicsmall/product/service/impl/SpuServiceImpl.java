package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.SpuDetailMapper;
import cn.tedu.anhuicsmall.product.mapper.SpuMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.SpuAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.SpuUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Category;
import cn.tedu.anhuicsmall.product.pojo.entity.Spu;
import cn.tedu.anhuicsmall.product.pojo.entity.SpuDetail;
import cn.tedu.anhuicsmall.product.service.ISpuService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 注入Spu详情的持久层接口
    @Autowired
    private SpuDetailMapper spuDetailMapper;

    /**
     * 处理添加Spu的数据
     *
     * @param spuAddNewDTO 添加的spu数据
     */
    @Override
    public void insert(SpuAddNewDTO spuAddNewDTO) {
        log.debug("开始处理添加Spu的业务,参数:{}", spuAddNewDTO);
        Spu spu = new Spu();
        BeanUtils.copyProperties(spuAddNewDTO, spu);
        int rows = spuMapper.insert(spu);
        if (rows > 1) {
            String message = "添加失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }

        log.debug("即将向spu详情表中添加数据...");
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetail.setDetail(spuDetail.getDetail());
        log.debug("开始向spu详情表中添加数据,参数:{}", spuDetail);
        int rowsToDetail = spuDetailMapper.insert(spuDetail);
        if (rowsToDetail > 1) {
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
        // 删除与该SpuId关联的详情信息
        Map<String, Object> map = new HashMap<>();
        map.put("spu_id", spuId);
        int rows = spuDetailMapper.deleteByMap(map);
        if (rows > 1) {
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
        // 判断修改的详情信息是否与原来的一致
        SpuDetail querySpuDetail = spuDetailMapper.selectById(spuUpdateDTO.getId());
        if (!querySpuDetail.getDetail().equals(spuUpdateDTO.getDetail())) { // 如果不一致,将详情表中内容也修改了
            SpuDetail spuDetail = new SpuDetail();
            spuDetail.setSpuId(spuUpdateDTO.getId());
            spuDetail.setDetail(spuUpdateDTO.getDetail());
            int rowsToDetail = spuDetailMapper.updateById(spuDetail);
            if (rowsToDetail > 1) {
                String message = "添加失败,服务器忙,请稍后再试...";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERR_INSERT, message);
            }
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
