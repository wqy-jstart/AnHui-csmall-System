package cn.tedu.anhuicsmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加属性的DTO类
 */
@Data
public class AttributeAddNewDTO implements Serializable {

    /**
     * 模板id
     */
    @ApiModelProperty(value = "模板id",required = true)
    private Long templateId;

    /**
     * 属性名称
     */
    @ApiModelProperty(value = "属性名称",required = true)
    private String name;

    /**
     * 属性描述
     */
    @ApiModelProperty(value = "属性描述",required = true)
    private String description;

    /**
     * 属性备选值列表
     */
    @ApiModelProperty(value = "属性备选值列表",required = true)
    private String valueList;

    /**
     * 计量单位
     */
    @ApiModelProperty(value = "计量单位",required = true)
    private String unit;

    /**
     * 属性排序
     */
    @ApiModelProperty(value = "属性排序",required = true)
    private Integer sort;
}
