package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收货地址实体类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@TableName("ups_address")
public class Address implements Serializable {

    /**
     * 收货地址id
     */
    @ApiModelProperty(value = "收货地址id",required = true)
    private Long id;

    /**
     * 收货用户id
     */
    @ApiModelProperty(value = "收货用户id",required = true)
    private Long userId;

    /**
     * 收货地址名称
     */
    @ApiModelProperty(value = "收货地址名称",required = true)
    private String name;

    /**
     * 数据创建时间
     */
    private Date gmtCreate;

    /**
     * 数据修改时间
     */
    private Date gmtModified;
}
