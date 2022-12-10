package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品SPU 详情表
 */
@Data
@TableName("ups_spu_detail")
public class SpuDetail implements Serializable {

    /**
     * 数据id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品spu的id
     */
    @ApiModelProperty(value = "SPU的id")
    private Long spuId;

    /**
     * 详情信息
     */
    @ApiModelProperty(value = "详情信息")
    @NotNull
    private String detail;

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
