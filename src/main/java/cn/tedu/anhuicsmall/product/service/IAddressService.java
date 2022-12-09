package cn.tedu.anhuicsmall.product.service;

import cn.tedu.anhuicsmall.product.pojo.dto.AddressAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AddressUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.UserLoginDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.UserUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Address;
import cn.tedu.anhuicsmall.product.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收货地址的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface IAddressService extends IService<Address> {


    /**
     * 处理添加收货地址的功能
     * @param addressAddNewDTO 添加需要的收货地址信息
     */
    void insert(AddressAddNewDTO addressAddNewDTO);

    /**
     * 处理根据id删除收货地址的功能
     * @param addressId 要删除的收货地址id
     */
    void deleteById(Long addressId);

    /**
     * 根据收货地址id修改收货地址
     * @param addressUpdateDTO 收货地址修改的信息
     */
    void update(AddressUpdateDTO addressUpdateDTO);

    /**
     * 根据id查询收货地址详情
     * @param addressId 收货地址id
     * @return 返回收货地址详情
     */
    Address selectById(Long addressId);

    /**
     * 处理查询后台收货地址列表的功能
     * @return 收货地址列表集合
     */
    List<Object> selectList();
}
