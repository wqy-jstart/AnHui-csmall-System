package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分类与属性模板的关联实体类
 */
@Data
@TableName("ups_category_attribute_template")
public class CategoryAndAttributeTemplate implements Serializable {

    /**
     * 关联表id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 类别id
     */
    @ApiModelProperty(value = "分类id",required = true)
    private Long categoryId;

    /**
     * 属性模板id
     */
    @ApiModelProperty(value = "属性模板id",required = true)
    private Long attributeTemplateId;

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
