package cn.tedu.anhuicsmall.product.mapper;

import cn.tedu.anhuicsmall.product.pojo.entity.User;
import cn.tedu.anhuicsmall.product.pojo.vo.UserListItemVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户(消费者)的持久层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
