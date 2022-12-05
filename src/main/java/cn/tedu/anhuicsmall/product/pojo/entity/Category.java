package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分类的实体类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@TableName("ups_category")
public class Category implements Serializable {

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
     * 分类深度
     */
    @ApiModelProperty(value = "分类深度",required = true)
    private Integer depth;

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
     * 是否为父级类别(0不是,1是)
     */
    @ApiModelProperty(value = "是否为父级(0不是,1是)",required = true)
    private Integer isParent;

    /**
     * 是否显示在导航栏(0显示,1不显示)
     */
    @ApiModelProperty(value = "是否显示导航栏(0显示,1不显示)",required = true)
    private Integer isDisplay;

    /**
     * 数据创建时间
     */
    private Date gmtCreate;

    /**
     * 数据修改时间
     */
    private Date gmtModified;

}
