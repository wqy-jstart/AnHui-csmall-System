package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.entity.Logistics;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 物流业务层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface ILogisticsService extends IService<Logistics> {

    /**
     * 查询物流信息列表
     * @return 返回列表
     */
    List<Object> selectList();
}
