package cn.tedu.anhuicsmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加属性模板的DTO类
 */
@Data
public class AttributeTemplateAddNewDTO implements Serializable {

    /**
     * 属性模板名称
     */
    @ApiModelProperty(value = "属性模板名称",required = true)
    private String name;

    /**
     * 属性模板拼音
     */
    @ApiModelProperty(value = "属性模板拼音",required = true)
    private String pinyin;

    /**
     * 属性模板关键字
     */
    @ApiModelProperty(value = "属性模板关键字",required = true)
    private String keywords;

    /**
     * 属性模板排序
     */
    @ApiModelProperty(value = "属性模板排序",required = true)
    private Integer sort;
}
