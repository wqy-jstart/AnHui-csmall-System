package cn.tedu.anhuicsmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类(消费者,主角)
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@TableName("ups_user")
public class User implements Serializable {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id",required = true)
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    /**
     * 密码(密文)
     */
    @ApiModelProperty(value = "密码(密文)",required = true)
    private String password;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别",required = true)
    private String gender;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄",required = true)
    private Integer age;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称",required = true)
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像",required = true)
    private String avatar;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件",required = true)
    private String email;

    /**
     * 个性签名
     */
    @ApiModelProperty(value = "个性签名",required = true)
    private String sign;

    /**
     * 数据创建时间
     */
    private Date gmtCreate;

    /**
     * 数据修改时间
     */
    private Date gmtModified;
}
