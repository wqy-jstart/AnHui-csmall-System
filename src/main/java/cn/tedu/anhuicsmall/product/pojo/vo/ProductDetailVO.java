package cn.tedu.anhuicsmall.product.pojo.vo;


import cn.tedu.anhuicsmall.product.pojo.entity.Attribute;
import cn.tedu.anhuicsmall.product.pojo.entity.Picture;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 主页商品的详情信息
 */
@Data
public class ProductDetailVO implements Serializable {

    /**
     * SPU商品id
     */
    private Long spuId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 显示在列表中的价格
     */
    @ApiModelProperty(value = "显示在列表中的价格")
    private Integer listPrice;

    /**
     * 显示在主页中的价格
     */
    @ApiModelProperty(value = "显示在主页中的价格")
    private Integer indexPrice;

    /**
     * 当前库存
     */
    @ApiModelProperty(value = "当前库存")
    private Integer stock;

    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    /**
     * 标签列表
     */
    @ApiModelProperty(value = "标签列表")
    private String tags;

    /**
     * 销量
     */
    @ApiModelProperty(value = "销量")
    private Integer sales;

    /**
     * 浏览量
     */
    @ApiModelProperty(value = "浏览量")
    private Integer views;

    /**
     * 详情信息
     */
    @ApiModelProperty(value = "spu的详情信息")
    private String detail;

    /**
     * 是否推荐(1=推荐;0=不推荐)
     */
    @ApiModelProperty(value = "是否推荐(1=推荐;0=不推荐)")
    private Integer isRecommend;

    /**
     * 图片封面
     */
    @ApiModelProperty(value = "图片封面")
    private String url;

    /**
     * 图片集合
     */
    @ApiModelProperty(value = "图片集合")
    private List<Picture> urls;

    /**
     * 属性集合
     */
    private List<Attribute> attributeList;

    /**
     * 数据创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
}
