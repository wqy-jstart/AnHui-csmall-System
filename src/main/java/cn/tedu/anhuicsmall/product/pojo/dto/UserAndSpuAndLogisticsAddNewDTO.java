package cn.tedu.anhuicsmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品物流关联实体类
 */
@Data
public class UserAndSpuAndLogisticsAddNewDTO implements Serializable {

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
}
