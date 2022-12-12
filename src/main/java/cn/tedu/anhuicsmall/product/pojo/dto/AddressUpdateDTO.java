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
     * 收货信息id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 收货地址名称
     */
    @ApiModelProperty(value = "收货地址名称",required = true)
    private String info;

    /**
     * 收货地址详细信息
     */
    @ApiModelProperty(value = "收货地址详细名称",required = true)
    private String detailInfo;

    /**
     * 收货手机号
     */
    @ApiModelProperty(value = "收货手机号",required = true)
    private String number;

    /**
     * 收货标签
     */
    @ApiModelProperty(value = "收货标签",required = true)
    private String tags;

    /**
     * 收件人姓名
     */
    @ApiModelProperty(value = "收件人姓名",required = true)
    private String name;
}
