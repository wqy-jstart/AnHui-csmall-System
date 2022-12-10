package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.SpuAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.SpuUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Spu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品SPU的业务层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface ISpuService extends IService<Spu> {

    /**
     * 处理添加Spu的功能
     *
     * @param spuAddNewDTO 添加的spu数据
     */
    void insert(SpuAddNewDTO spuAddNewDTO);

    /**
     * 处理根据id删除SPU的功能
     *
     * @param spuId 要删除的spuId
     */
    void deleteById(Long spuId);

    /**
     * 处理根据id修改Spu的数据
     *
     * @param spuUpdateDTO 修改的数据
     */
    void update(SpuUpdateDTO spuUpdateDTO);

    /**
     * 根据id查询spu数据
     *
     * @param spuId spuId
     * @return 返回实体类
     */
    Spu selectById(Long spuId);

    /**
     * 查询未上架的Spu列表
     *
     * @return 返回列表
     */
    List<Object> selectByNotIsP();

    /**
     * 查询既审核又上架的Spu列表
     *
     * @return 返回列表
     */
    List<Object> selectByIsPC();

    /**
     * 处理上架Spu的功能
     *
     * @param id 要启用的SpuId
     */
    void setPublish(Long id);

    /**
     * 处理下架Spu的功能
     *
     * @param id 要禁用的SpId
     */
    void setNotPublish(Long id);

    /**
     * 处理推荐Spu的功能
     *
     * @param id 要推荐的SpuId
     */
    void setRecommend(Long id);

    /**
     * 处理不推荐Spu的功能
     *
     * @param id 不推荐的SpId
     */
    void setNotRecommend(Long id);

    /**
     * 处理审核Spu的功能
     *
     * @param id 要启用的SpuId
     */
    void setCheck(Long id);

    /**
     * 处理未审核Spu的功能
     *
     * @param id 要禁用的SpuId
     */
    void setNotCheck(Long id);
}
