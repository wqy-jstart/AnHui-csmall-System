package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 物流配送
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@TableName("ups_logistics")
public class Logistics implements Serializable {

    /**
     * 物流id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 物流名称
     */
    @ApiModelProperty(value = "物流名称",required = true)
    private String name;

    /**
     * 物流备注
     */
    @ApiModelProperty(value = "物流备注",required = true)
    private String note;

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
