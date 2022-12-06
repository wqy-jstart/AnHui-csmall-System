package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车实体类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@TableName("ups_cart")
public class Cart implements Serializable {

    /**
     * 购物车id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 消费者用户id
     */
    @ApiModelProperty(value = "消费者用户id",required = true)
    private Long userId;

    /**
     * SPU的id
     */
    @ApiModelProperty(value = "商品spu的id",required = true)
    private Long spuId;

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
