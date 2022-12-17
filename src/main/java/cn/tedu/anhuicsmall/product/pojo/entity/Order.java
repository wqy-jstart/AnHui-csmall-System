package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
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
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;

    /**
     * SPU商品详情id
     */
    @ApiModelProperty(value = "SPU商品详情id", required = true)
    private Long spuId;

    /**
     * 收货地址id
     */
    @ApiModelProperty(value = "收货地址id", required = true)
    private Long addressId;

    /**
     * 物流id
     */
    @ApiModelProperty(value = "物流id", required = true)
    private Long logisticsId;

    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量", required = true)
    private Integer number;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号", required = true)
    private String outTradeNo;

    /**
     * 交易金额
     */
    @ApiModelProperty(value = "交易金额", required = true)
    private Double totalAmount;

    /**
     * 交易流水号
     */
    @ApiModelProperty(value = "交易流水号")
    private String tradeNo;

    /**
     * 付款时间
     */
    @ApiModelProperty(value = "付款时间")
    private Date time;

    /**
     * 是否已付款
     */
    @ApiModelProperty(value = "是否已付款")
    private Integer isPay;

    /**
     * 是否已发货
     */
    @ApiModelProperty(value = "是否已发货")
    private Integer isDistribute;

    /**
     * 是否已收货
     */
    @ApiModelProperty(value = "是否已收货")
    private Integer isTake;

    /**
     * 是否已评价
     */
    @ApiModelProperty(value = "是否已评价")
    private Integer isComment;

    /**
     * 是否退货
     */
    @ApiModelProperty(value = "是否退货")
    private Integer isBack;

    /**
     * 退货理由
     */
    @ApiModelProperty(value = "退货理由")
    private String backText;

    /**
     * 数据创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 数据修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
