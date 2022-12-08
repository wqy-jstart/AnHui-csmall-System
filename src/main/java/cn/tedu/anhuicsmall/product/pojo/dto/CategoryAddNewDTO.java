package cn.tedu.anhuicsmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加分类的DTO类
 */
@Data
public class CategoryAddNewDTO implements Serializable {

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称",required = true)
    private String name;

    /**
     * 分类的父级id
     */
    @ApiModelProperty(value = "分类父级id",required = true)
    private Long parentId;

    /**
     * 分类关键字
     */
    @ApiModelProperty(value = "分类关键字",required = true)
    private String keywords;

    /**
     * 分类排序
     */
    @ApiModelProperty(value = "分类排序",required = true)
    private Integer sort;

    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用分类",required = true)
    private Integer enable;

    /**
     * 是否显示在导航栏(0显示,1不显示)
     */
    @ApiModelProperty(value = "是否显示导航栏(0显示,1不显示)",required = true)
    private Integer isDisplay;
}
