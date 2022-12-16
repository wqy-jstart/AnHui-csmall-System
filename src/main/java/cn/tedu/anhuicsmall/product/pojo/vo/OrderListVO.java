package cn.tedu.anhuicsmall.product.pojo.vo;

import cn.tedu.anhuicsmall.product.pojo.entity.Attribute;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 返回订单列表的VO类
 */
@Data
public class OrderListVO implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * spuId
     */
    private Long spuId;

    /**
     * 收货地址名称
     */
    @ApiModelProperty(value = "收货地址名称")
    private String addressName;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String spuName;

    /**
     * 商品封面
     */
    @ApiModelProperty(value = "商品封面")
    private String url;

    /**
     * 收货地址
     */
    @ApiModelProperty(value = "收货地址")
    private String info;

    /**
     * 收货详细地址
     */
    @ApiModelProperty(value = "收货详细地址")
    private String detailInfo;

    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码")
    private String pnumber;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private String tags;

    /**
     * 物流名称
     */
    @ApiModelProperty(value = "物流名称")
    private String logisticsName;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer number;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String outTradeNo;

    /**
     * 交易金额
     */
    @ApiModelProperty(value = "交易金额")
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
     * 属性集合
     */
    private List<Attribute> attributeList;

}
