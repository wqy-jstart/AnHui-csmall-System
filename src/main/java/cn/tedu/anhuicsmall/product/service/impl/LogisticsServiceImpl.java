package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.mapper.LogisticsMapper;
import cn.tedu.anhuicsmall.product.pojo.entity.Logistics;
import cn.tedu.anhuicsmall.product.service.ILogisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物流的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements ILogisticsService {
    public LogisticsServiceImpl(){
        log.debug("创建业务层接口实现类:LogisticsServiceImpl");
    }

    // 注入物流信息的持久层接口
    @Autowired
    private LogisticsMapper logisticsMapper;

    /**
     * 查询物流信息列表的业务
     * @return 返回列表
     */
    @Override
    public List<Object> selectList() {
        log.debug("开始处理查询物流信息列表的业务,无参!");
        return logisticsMapper.selectObjs(null);
    }
}
