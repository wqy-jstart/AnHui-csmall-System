package cn.tedu.anhuicsmall.product.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Spu商品显示主页的列表
 */
@Data
public class SpuIndexListVO implements Serializable {

    /**
     * SpuId
     */
    @ApiModelProperty(value = "spuId")
    private Long spuId;

    /**
     * Spu标题
     */
    @ApiModelProperty(value = "Spu标题")
    private String title;

    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格")
    private Integer listPrice;

    /**
     * 商品浏览量
     */
    @ApiModelProperty(value = "商品浏览量")
    private Integer views;

    /**
     * 商品销量
     */
    @ApiModelProperty(value = "商品销量")
    private Integer sales;

    /**
     * 商品的封面图片
     */
    @ApiModelProperty(value = "商品的封面图片")
    private String url;
}
