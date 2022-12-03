package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.UserMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.UserLoginDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.User;
import cn.tedu.anhuicsmall.product.pojo.vo.UserListItemVO;
import cn.tedu.anhuicsmall.product.service.IUserService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
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
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService{

    public UserServiceImpl(){
        log.debug("创建业务层接口实现类:UserServiceImpl");
    }

    // 注入用户持久层接口
    @Autowired
    private UserMapper userMapper;

    /**
     * 处理用户注册的业务
     * @param userLoginDTO 注册需要的用户信息
     */
    @Override
    public void insert(UserLoginDTO userLoginDTO) {
        log.debug("开始处理用户注册的功能!参数:{}",userLoginDTO);
        // 判断用户名是否存在(根据用户名检查是否能查询到信息)
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",userLoginDTO.getUsername());
        User queryUser = userMapper.selectOne(wrapper);
        if (queryUser==null){
            String message = "注册失败,该用户名已存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
        }
        log.debug("即将向用户表中插入数据...");
        User user = new User();
        BeanUtils.copyProperties(userLoginDTO,user);
        log.debug("开始向用户表中插入数据,参数:{}",user);
        int rows = userMapper.insert(user);
        if (rows>1){
            String message = "注册失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }
    }

    /**
     * 查询后台用户列表的功能
     * @return 返回用户列表的集合
     */
    @Override
    public List<Object> selectList() {
        log.debug("开始处理查询用户列表的功能,无参!");
        return userMapper.selectObjs(null);
    }
}
