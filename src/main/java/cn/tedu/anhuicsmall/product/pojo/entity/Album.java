package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 这是相册的实体类,包含相册的所有信息
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@TableName("ups_album")
public class Album implements Serializable {

    /**
     * 相册id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
