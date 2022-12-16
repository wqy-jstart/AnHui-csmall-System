package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品物流关联实体类
 */
@Data
@TableName("ups_user_spu_logistics")
public class UserAndSpuAndLogistics implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户Id")
    private Long userId;

    /**
     * SPU商品id
     */
    @ApiModelProperty(value = "商品spu的Id")
    private Long spuId;

    /**
     * SPU商品id
     */
    @ApiModelProperty(value = "物流的Id")
    private Long logisticsId;

    /**
     * 物流备注
     */
    @ApiModelProperty(value = "物流备注")
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
