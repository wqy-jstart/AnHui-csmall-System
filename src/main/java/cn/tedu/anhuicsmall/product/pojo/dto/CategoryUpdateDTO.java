package cn.tedu.anhuicsmall.product.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改分类的DTO类
 */
@Data
public class CategoryUpdateDTO implements Serializable {

    /**
     * 分类id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
}
