package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 属性的实体类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@TableName("ups_attribute")
public class Attribute implements Serializable {

    /**
     * 属性id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 模板id
     */
    @ApiModelProperty(value = "模板id",required = true)
    @NotNull
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
