package cn.tedu.anhuicsmall.product.mapper;

import cn.tedu.anhuicsmall.product.pojo.entity.Spu;
import cn.tedu.anhuicsmall.product.pojo.vo.SpuIndexListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SPU商品详情的持久层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Repository
public interface SpuMapper extends BaseMapper<Spu> {

    /**
     * 查询主页列表数据
     * @return 返回List集合
     */
    List<SpuIndexListVO> selectIndexSpu();
}
