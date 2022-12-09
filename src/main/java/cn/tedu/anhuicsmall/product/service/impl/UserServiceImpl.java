package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.CartMapper;
import cn.tedu.anhuicsmall.product.mapper.SpuMapper;
import cn.tedu.anhuicsmall.product.mapper.UserMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.UserLoginDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.UserUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Cart;
import cn.tedu.anhuicsmall.product.pojo.entity.Spu;
import cn.tedu.anhuicsmall.product.pojo.entity.User;
import cn.tedu.anhuicsmall.product.pojo.vo.UserListItemVO;
import cn.tedu.anhuicsmall.product.pojo.vo.UserStandardVO;
import cn.tedu.anhuicsmall.product.service.IUserService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    public UserServiceImpl() {
        log.debug("创建业务层接口实现类:UserServiceImpl");
    }

    // 注入用户持久层接口
    @Autowired
    private UserMapper userMapper;

    // 注入购物车的持久层接口
    @Autowired
    private CartMapper cartMapper;

    // 注入商品spu的持久层接口
    @Autowired
    private SpuMapper spuMapper;

    /**
     * 处理用户注册的业务
     *
     * @param userLoginDTO 注册需要的用户信息
     */
    @Override
    public void insert(UserLoginDTO userLoginDTO) {
        log.debug("开始处理用户注册的功能!参数:{}", userLoginDTO);
        // 判断用户名是否存在(根据用户名检查是否能查询到信息)
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userLoginDTO.getUsername());
        User queryUser = userMapper.selectOne(wrapper);
        if (queryUser != null) {
            String message = "注册失败,该用户名已存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        log.debug("即将向用户表中插入数据...");
        User user = new User();
        BeanUtils.copyProperties(userLoginDTO, user);
        log.debug("开始向用户表中插入数据,参数:{}", user);
        int rows = userMapper.insert(user);
        if (rows > 1) {
            String message = "注册失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    /**
     * 处理用户登录的业务
     * @param userLoginDTO 登录提供的用户信息
     */
    @Override
    public void login(UserLoginDTO userLoginDTO) {
        log.debug("开始处理用户登录的业务,参数:{}",userLoginDTO);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",userLoginDTO.getUsername());
        User queryUser = userMapper.selectOne(wrapper);
        if (queryUser == null){
            String message = "登录失败,用户名不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }
        if (!queryUser.getPassword().equals(userLoginDTO.getPassword())){
            String message = "登录失败,密码错误,请重新输入!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
        }

    }

    /**
     * 根据id删除用户信息的功能
     *
     * @param userId 要删除的用户id
     */
    @Override
    public void deleteById(Long userId) {
        log.debug("开始处理删除用户名为[{}]的用户信息", userId);
        // 先根据id查看用户是否存在
        User queryUser = userMapper.selectById(userId);
        if (queryUser == null) {
            String message = "删除失败,该用户不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 如果用户关联的购物车信息不为空,则不能删除
        QueryWrapper<Cart> wrapperToCart = new QueryWrapper<>();
        wrapperToCart.eq("user_id", userId);
        Cart queryCart = cartMapper.selectOne(wrapperToCart);
        if (queryCart != null) { // 这里需判断购物车结果不为Null,否则引发空指针
            QueryWrapper<Spu> wrapperToSpu = new QueryWrapper<>();
            wrapperToSpu.eq("id", queryCart.getSpuId());
            Spu querySpu = spuMapper.selectOne(wrapperToSpu);
            if (querySpu != null) {
                String message = "删除失败,该用户包含关联的购物信息!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        log.debug("即将执行删除id为[{}]的用户信息", userId);
        int rows = userMapper.deleteById(userId);
        if (rows > 1) {
            String message = "删除失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    /**
     * 开始处理根据id修改用户信息的功能
     *
     * @param userUpdateDTO 用户修改的信息
     */
    @Override
    public void update(UserUpdateDTO userUpdateDTO) {
        log.debug("开始处理修改id为[{}]的用户信息", userUpdateDTO.getId());
        User queryUser = userMapper.selectById(userUpdateDTO.getId());
        if (queryUser == null) {
            String message = "修改失败,该用户数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        log.debug("即将修改id为[{}]的用户信息...", userUpdateDTO.getId());
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO,user);
        int rows = userMapper.updateById(user);
        if (rows > 1) {
            String message = "修改失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    /**
     * 开始处理根据id查询详情的功能
     *
     * @param userId 用户id
     * @return 返回用户实体类
     */
    @Override
    public User selectById(Long userId) {
        log.debug("开始处理查询id为[{}]的用户信息", userId);
        User user = userMapper.selectById(userId);
        if (user == null) {
            String message = "查询失败,该用户信息不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return user;
    }

    /**
     * 处理根据用户名查询用户信息的功能
     * @param username 用户名
     * @return 返回结果集
     */
    @Override
    public User selectByUserName(String username) {
        log.debug("开始处理根据用户名{}查询该用户的信息功能",username);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return userMapper.selectOne(wrapper);
    }

    /**
     * 查询后台用户列表的功能
     *
     * @return 返回用户列表的集合
     */
    @Override
    public List<Object> selectList() {
        log.debug("开始处理查询用户列表的功能,无参!");
        return userMapper.selectObjs(null);
    }
}
