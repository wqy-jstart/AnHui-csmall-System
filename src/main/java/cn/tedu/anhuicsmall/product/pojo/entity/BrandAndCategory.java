package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 品牌与类别关联实体类
 */
@Data
@TableName("ups_brand_category")
public class BrandAndCategory implements Serializable {

    /**
     * 数据id
     */
    private Long id;

    /**
     * 品牌id
     */
    @ApiModelProperty(value = "品牌id",required = true)
    private Long brandId;

    /**
     * 类别id
     */
    @ApiModelProperty(value = "类别id",required = true)
    private Long categoryId;

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
