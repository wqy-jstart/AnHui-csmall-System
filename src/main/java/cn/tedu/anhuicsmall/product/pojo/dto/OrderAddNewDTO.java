package cn.tedu.anhuicsmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加订单的DTO类
 *
 */
@Data
public class OrderAddNewDTO implements Serializable {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id",required = true)
    private Long userId;

    /**
     * SPU商品详情id
     */
    @ApiModelProperty(value = "SPU商品详情id",required = true)
    private Long spuId;

    /**
     * 收货地址id
     */
    @ApiModelProperty(value = "收货地址id",required = true)
    private Long addressId;

    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量",required = true)
    private Integer number;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号",required = true)
    private String outTradeNo;

    /**
     * 交易金额
     */
    @ApiModelProperty(value = "交易金额",required = true)
    private Double totalAmount;

    /**
     * 退货理由
     */
    @ApiModelProperty(value = "退货理由")
    private String backText;

    /**
     * 交易流水号
     */
    @ApiModelProperty(value = "交易流水号")
    private String tradeNo;
}
