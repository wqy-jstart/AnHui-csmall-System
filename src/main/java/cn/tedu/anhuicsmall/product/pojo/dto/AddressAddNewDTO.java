package cn.tedu.anhuicsmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 添加收货地址的DTO类
 */
@Data
public class AddressAddNewDTO implements Serializable {

    /**
     * 收货用户id
     */
    @ApiModelProperty(value = "收货用户id",required = true)
    private Long userId;

    /**
     * 收货地址名称
     */
    @ApiModelProperty(value = "收货地址名称",required = true)
    @NotNull
    private String name;
}
