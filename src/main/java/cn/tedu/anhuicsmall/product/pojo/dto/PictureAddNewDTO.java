package cn.tedu.anhuicsmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加图片的DTO类
 */
@Data
public class PictureAddNewDTO implements Serializable {

    /**
     * 相册id
     */
    @ApiModelProperty(value = "相册id",required = true)
    private Long albumId;

    /**
     * 图片路径
     */
    @ApiModelProperty(value = "图片路径",required = true)
    private String url;

    /**
     * 图片描述
     */
    @ApiModelProperty(value = "图片描述",required = true)
    private String description;

    /**
     * 图片宽
     */
    @ApiModelProperty(value = "图片宽",required = true)
    private Integer width;

    /**
     * 图片高
     */
    @ApiModelProperty(value = "图片高",required = true)
    private Integer height;

    /**
     * 是否作为封面
     */
    @ApiModelProperty(value = "是否作为封面",required = true)
    private Integer isCover;

    /**
     * 图片排序
     */
    @ApiModelProperty(value = "图片排序",required = true)
    private Integer sort;
}
