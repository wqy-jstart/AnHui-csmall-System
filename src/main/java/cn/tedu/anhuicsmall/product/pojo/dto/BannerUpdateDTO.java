package cn.tedu.anhuicsmall.product.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改轮播图的DTO类
 */
@Data
public class BannerUpdateDTO implements Serializable {


    /**
     * 轮播图id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品spu的id
     */
    @ApiModelProperty(value = "spu的id",required = true)
    private Long spuId;

    /**
     * 图片路径
     */
    @ApiModelProperty(value = "路径",required = true)
    @NotNull
    private String url;

    /**
     * 轮播图简介
     */
    @ApiModelProperty(value = "简介",required = true)
    private String description;

    /**
     * 是否启用
     */
    @ApiModelProperty(value = "1=启用;0=禁用", required = true)
    private Integer enable;

    /**
     * 轮播图排序
     */
    @ApiModelProperty(value = "排序",required = true)
    private Integer sort;
}
