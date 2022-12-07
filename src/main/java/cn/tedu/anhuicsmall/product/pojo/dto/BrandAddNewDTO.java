package cn.tedu.anhuicsmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加品牌的DTO类
 *
 */
@Data
public class BrandAddNewDTO implements Serializable {

    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称",required = true)
    private String name;

    /**
     * 品牌拼音
     */
    @ApiModelProperty(value = "品牌拼音",required = true)
    private String pinyin;

    /**
     * 品牌Logo
     */
    @ApiModelProperty(value = "品牌Logo",required = true)
    private String logo;

    /**
     * 品牌描述
     */
    @ApiModelProperty(value = "品牌描述",required = true)
    private String description;

    /**
     * 品牌关键字
     */
    @ApiModelProperty(value = "品牌关键字",required = true)
    private String keywords;

    /**
     * 品牌排序
     */
    @ApiModelProperty(value = "品牌排序",required = true)
    private Integer sort;

    /**
     * 品牌销量
     */
    @ApiModelProperty(value = "品牌销量",required = true)
    private Integer sales;

    /**
     * 是否启用品牌
     */
    @ApiModelProperty(value = "是否启用品牌",required = true)
    private Integer enable;
}
