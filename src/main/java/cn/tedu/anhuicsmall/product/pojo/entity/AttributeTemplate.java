package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 属性模板的实体类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@TableName("ups_attribute_template")
public class AttributeTemplate implements Serializable {

    /**
     * 属性模板id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
