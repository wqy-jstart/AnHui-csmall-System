package cn.tedu.anhuicsmall.product.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 要修改的收货地址DTO
 */
@Data
public class AddressUpdateDTO implements Serializable {

    /**
     * 收货地址id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 收货用户id
     */
    @ApiModelProperty(value = "收货用户id",required = true)
    private Long userId;

    /**
     * 收货地址名称
     */
    @ApiModelProperty(value = "收货地址名称",required = true)
    private String name;
}
