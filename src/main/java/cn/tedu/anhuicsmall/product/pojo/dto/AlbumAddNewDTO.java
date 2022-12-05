package cn.tedu.anhuicsmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加相册的DTO类
 *
 */
@Data
public class AlbumAddNewDTO implements Serializable {

    /**
     * 相册名称
     */
    @ApiModelProperty(value = "相册名称",required = true)
    private String name;

    /**
     * 相册描述
     */
    @ApiModelProperty(value = "相册描述",required = true)
    private String description;

    /**
     * 相册排序
     */
    @ApiModelProperty(value = "相册排序",required = true)
    private Integer sort;
}
