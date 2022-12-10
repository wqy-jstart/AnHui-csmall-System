package cn.tedu.anhuicsmall.product.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改Spu的DTO类
 */
@Data
public class SpuUpdateDTO implements Serializable {

    /**
     * SPU商品id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * SPU名称
     */
    @ApiModelProperty(value = "SPU名称")
    private String name;

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
     * 标签列表
     */
    @ApiModelProperty(value = "标签列表")
    private String tags;

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
}
