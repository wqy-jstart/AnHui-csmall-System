package cn.tedu.anhuicsmall.product.pojo.vo;

import cn.tedu.anhuicsmall.product.pojo.entity.Attribute;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询购物车列表信息VO类
 */
@Data
public class CartListVO implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * spuId
     */
    private Long spuId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value ="名称")
    private String name;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 显示在主页中的价格
     */
    @ApiModelProperty(value = "显示在主页中的价格")
    private Integer indexPrice;

    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    /**
     * 标签列表
     */
    @ApiModelProperty(value = "标签列表")
    private String tags;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "数量")
    private Integer num;

    /**
     * 详情信息
     */
    @ApiModelProperty(value = "spu的详情信息")
    private String detail;

    /**
     * 图片封面
     */
    @ApiModelProperty(value = "图片封面")
    private String url;

    /**
     * 价格总和
     */
    @ApiModelProperty(value = "购物车商品价格总和")
    private Integer sumPrice;
}
