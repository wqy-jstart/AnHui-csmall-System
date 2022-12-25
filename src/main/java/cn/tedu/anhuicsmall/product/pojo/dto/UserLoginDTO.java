package cn.tedu.anhuicsmall.product.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户注册时提供的DTO类
 */
@Data
public class UserLoginDTO implements Serializable {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    /**
     * 密码(密文)
     */
    @ApiModelProperty(value = "密码(密文)",required = true)
    @NotNull
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称",hidden = true)
    @NotNull
    private String nickname;
}
