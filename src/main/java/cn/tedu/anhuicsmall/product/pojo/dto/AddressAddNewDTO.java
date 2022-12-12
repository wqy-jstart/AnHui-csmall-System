package cn.tedu.anhuicsmall.product.pojo.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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
