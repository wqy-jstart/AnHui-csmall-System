package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.UserAndSpuAndLogisticsAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.UserAndSpuAndLogistics;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户和Spu和物流的业务层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface IUserAndSpuAndLogisticsService extends IService<UserAndSpuAndLogistics> {

    /**
     * 处理发货时添加的关联表信息
     * @param uslDTO 信息
     */
    void insert(UserAndSpuAndLogisticsAddNewDTO uslDTO);
}
