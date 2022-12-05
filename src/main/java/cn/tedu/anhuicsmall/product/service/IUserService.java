package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.UserLoginDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.User;
import cn.tedu.anhuicsmall.product.pojo.vo.UserListItemVO;
import cn.tedu.anhuicsmall.product.pojo.vo.UserStandardVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户的业务层接口类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
public interface IUserService extends IService<User> {

    /**
     * 处理用户注册时添加用户的功能
     * @param userLoginDTO 注册需要的用户信息
     */
    void insert(UserLoginDTO userLoginDTO);

    /**
     * 处理根据id删除用户的功能
     * @param userId 要删除的用户id
     */
    void deleteById(Long userId);

    /**
     * 处理查询后台用户列表的功能
     * @return 用户列表集合
     */
    List<Object> selectList();
}
