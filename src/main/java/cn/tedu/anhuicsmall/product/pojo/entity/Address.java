package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收货信息实体类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@TableName("ups_address")
public class Address implements Serializable {

    /**
     * 收货信息id
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
