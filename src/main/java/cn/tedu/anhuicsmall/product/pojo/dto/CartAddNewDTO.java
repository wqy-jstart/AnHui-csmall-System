package cn.tedu.anhuicsmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 添加购物车的DTO类
 */
@Data
public class CartAddNewDTO implements Serializable {

    /**
     * 消费者用户id
     */
    @ApiModelProperty(value = "消费者用户id",required = true)
    @NotNull
    private Long userId;

    /**
     * SPU的id
     */
    @ApiModelProperty(value = "商品spu的id",required = true)
    @NotNull
    private Long spuId;

}
