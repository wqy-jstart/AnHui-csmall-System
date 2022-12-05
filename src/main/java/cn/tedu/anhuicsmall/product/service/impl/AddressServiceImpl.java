package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.AddressMapper;
import cn.tedu.anhuicsmall.product.mapper.UserMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.AddressAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AddressUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Address;
import cn.tedu.anhuicsmall.product.pojo.entity.User;
import cn.tedu.anhuicsmall.product.service.IAddressService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收货地址的业务层接口类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

    public AddressServiceImpl() {
        log.debug("创建业务层接口实现类:AddressServiceImpl");
    }

    // 注入收货地址的持久层接口
    @Autowired
    private AddressMapper addressMapper;

    // 注入用户的持久层接口
    @Autowired
    private UserMapper userMapper;

    /**
     * 处理添加用户对应的收货信息
     *
     * @param addressAddNewDTO 注册需要的收货地址信息
     */
    @Override
    public void insert(AddressAddNewDTO addressAddNewDTO) {
        log.debug("开始处理添加收货地址的业务,参数:{}", addressAddNewDTO);
        // 检查用户id是否存在
        User queryUser = userMapper.selectById(addressAddNewDTO.getUserId());
        if (queryUser == null) {
            String message = "添加失败,该用户id不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        log.debug("即将向收货地址表中插入信息...");
        Address address = new Address();
        BeanUtils.copyProperties(addressAddNewDTO, address);
        int rows = addressMapper.insert(address);
        if (rows > 1) {
            String message = "添加失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }

    }

    /**
     * 处理根据id删除收货地址的功能
     *
     * @param addressId 要删除的收货地址id
     */
    @Override
    public void deleteById(Long addressId) {
        log.debug("开始处理删除id为{}的收货地址信息", addressId);
        // 判断该收货地址id是否存在
        Address queryAddress = addressMapper.selectById(addressId);
        if (queryAddress == null) {
            String message = "删除失败,该收货地址不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        log.debug("即将执行id为{}收货地址的删除功能...", addressId);
        int rows = addressMapper.deleteById(addressId);
        if (rows > 1) {
            String message = "删除失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    /**
     * 根据id修改收货地址信息的功能
     *
     * @param addressUpdateDTO 收货地址修改的信息
     */
    @Override
    public void update(AddressUpdateDTO addressUpdateDTO) {
        log.debug("开始处理修改id:[{}]的收货地址信息", addressUpdateDTO.getId());
        // 判断该收货地址id是否存在
        Address queryAddress = addressMapper.selectById(addressUpdateDTO.getId());
        if (queryAddress == null) {
            String message = "修改失败,该收货地址不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        User queryUser = userMapper.selectById(queryAddress.getUserId());
        if (queryUser == null){
            String message = "修改失败,该收货地址对应的用户不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        Address address = new Address();
        BeanUtils.copyProperties(addressUpdateDTO, address);
        log.debug("开始执行对id为{}的收货地址修改功能...", addressUpdateDTO.getId());
        int rows = addressMapper.updateById(address);
        if (rows > 1) {
            String message = "修改失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }

    }

    /**
     * 处理根据id查询收货地址详情信息
     *
     * @param addressId 收货地址id
     * @return 返回收货地址的实体类
     */
    @Override
    public Address selectById(Long addressId) {
        log.debug("开始处理查询id{}的收货地址详情", addressId);
        Address queryAddress = addressMapper.selectById(addressId);
        if (queryAddress == null) {
            String message = "查询失败,该收货地址数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        return queryAddress;
    }

    /**
     * 处理查询收货地址列表的功能
     *
     * @return 返回收货地址的实体类
     */
    @Override
    public List<Object> selectList() {
        log.debug("开始处理查询收货地址列表信息的功能,无参!");
        return addressMapper.selectObjs(null);
    }
}
