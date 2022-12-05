package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单信息
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@TableName("ups_order")
public class Order implements Serializable {

    /**
     * 订单id(UUID)
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id",required = true)
    private Long userId;

    /**
     * SPU商品详情id
     */
    @ApiModelProperty(value = "SPU商品详情id",required = true)
    private Long supId;

    /**
     * 收货地址id
     */
    @ApiModelProperty(value = "收货地址id",required = true)
    private Long addressId;

    /**
     * 物流id
     */
    @ApiModelProperty(value = "物流id",required = true)
    private Long logisticsId;

    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量",required = true)
    private Integer number;

    /**
     * 数据创建时间
     */
    private Date gmtCreate;

    /**
     * 数据修改时间
     */
    private Date gmtModified;
}
