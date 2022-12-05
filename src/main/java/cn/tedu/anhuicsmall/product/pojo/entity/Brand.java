package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 品牌的实体类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@TableName("ups_brand")
public class Brand implements Serializable {

    /**
     * 品牌id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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

    /**
     * 数据创建时间
     */
    private Date gmtCreate;

    /**
     * 数据修改时间
     */
    private Date gmtModified;
}
