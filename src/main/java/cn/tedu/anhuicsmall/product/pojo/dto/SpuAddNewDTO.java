package cn.tedu.anhuicsmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加Spu的DTO类
 */
@Data
public class SpuAddNewDTO implements Serializable {

    /**
     * SPU名称
     */
    @ApiModelProperty(value = "SPU名称")
    private String name;

    /**
     * SPU编号
     */
    @ApiModelProperty(value = "SPU编号")
    private String typeNumber;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 显示在列表中的价格
     */
    @ApiModelProperty(value = "显示在列表中的价格")
    private Integer listPrice;

    /**
     * 当前库存
     */
    @ApiModelProperty(value = "当前库存")
    private Integer stock;

    /**
     * 库存阈警值
     */
    @ApiModelProperty(value = "库存阈警值")
    private Integer stockThreshold;

    /**
     * 计价单位
     */
    @ApiModelProperty(value = "计价单位")
    private String unit;

    /**
     * 品牌id
     */
    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    /**
     * 属性模板id
     */
    @ApiModelProperty(value = "属性模板id")
    private Long attributeTemplateId;

    /**
     * 相册id
     */
    @ApiModelProperty(value = "相册id")
    private Long albumId;

    /**
     * 图片集合
     */
    @ApiModelProperty(value = "图片集合")
    private String pictures;

    /**
     * 关键字列表
     */
    @ApiModelProperty(value = "关键字列表")
    private String keywords;

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
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 详情信息
     */
    @ApiModelProperty(value = "spu的详情信息")
    private String detail;

    /**
     * 是否上架(1=已上架;0=已下架)
     */
    @ApiModelProperty(value = "是否上架(1=已上架;0=已下架)")
    private Integer isPublished;

    /**
     * 是否推荐(1=推荐;0=不推荐)
     */
    @ApiModelProperty(value = "是否推荐(1=推荐;0=不推荐)")
    private Integer isRecommend;

    /**
     * 是否审核(1=已审核'0=未审核)
     */
    @ApiModelProperty(value = "是否审核(1=已审核'0=未审核)")
    private Integer isChecked;

    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人")
    private String checkUser;
}
