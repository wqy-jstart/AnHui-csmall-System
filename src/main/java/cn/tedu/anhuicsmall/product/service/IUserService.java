package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.UserLoginDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.UserUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户的业务层接口类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional // 开启基于Spring JDBC的事务注解
public interface IUserService extends IService<User> {

    /**
     * 处理用户注册时添加用户的功能
     * @param userLoginDTO 注册需要的用户信息
     */
    void insert(UserLoginDTO userLoginDTO);

    /**
     * 处理用户登录的功能
     * @param userLoginDTO 登录提供的用户信息
     */
    void login(UserLoginDTO userLoginDTO);

    /**
     * 处理根据id删除用户的功能
     * @param userId 要删除的用户id
     */
    void deleteById(Long userId);

    /**
     * 根据用户id修改用户
     * @param userUpdateDTO 用户修改的信息
     */
    void update(UserUpdateDTO userUpdateDTO);

    /**
     * 根据id查询用户详情
     * @param userId 用户id
     * @return 返回用户详情
     */
    User selectById(Long userId);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 返回用户实体类
     */
    User selectByUserName(String username);

    /**
     * 处理查询后台用户列表的功能
     * @return 用户列表集合
     */
    List<Object> selectList();
}
