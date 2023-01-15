package cn.tedu.anhuicsmall.product.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 支付宝的支付控制类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Api(tags = "12.支付模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    public PayController(){
        log.debug("创建支付控制器类：PayController");
    }

    // APPId
    private static final String APP_ID = "2021000122602796";
    private static final String APP_PRIVATE_KET = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCKUze0UpXQZS2mGi7pNNjIi5IlURrN6FndmpFWt/sGHQ7LmM7nPscn8dUngFzOenkK9+czM89gCmTllHm95bVlI5Qot6zSa0AjlzDMvCD4F3CzSvui8rdke8OFUGDXeQ7QUONQV+WLbsq2U9ANAg3wkwFDM2y0fp3SlvHnPjFYeFPfODddz0oLX2qoAdzfFXkStrFXvx9Lxg1yYcJbaGJtizcy0zYFUQph4tgZQ+yAQF+fvS8FC7t4nwsWJm53g5Egf//9Pcp4Kbs9QsTCSYWpim1ZZ9rRFhPCxyj5xwznyQG0ar7o+9jsZpu6Y+KJDl+35qVjUyGNR4CNOEgLmhdBAgMBAAECggEAcdGD1obV60I1y6Vj0FTBxFLEEJyp96Amwee7i6Wbki46zGCSb0kCyuV8xSIkO2ofS10tCr6o9pfW6enAOvdWyT/7W323YPULM73mU7DBWCLBg2ZBpJUfPjlwarP225YrEFJemO+aW57wghJ48RstqTTtJwSbf/Fnn67AocxVAjvaL+xwLKv+3VX2QiY/9WmfsLBxQqU6jVJLP2FwKtpqzqzNUfq+8WY3G7BMcTFVJj/QEBSZ97jE837xu9/BNKctk4VTncvSGEjpZ+2pWyrf7pVE3Z+lRayJzx7pk3pCoCRbWKCxmgD4u76KEJgvsIi0vQHPKUcfCKHc+btziLlsIQKBgQDJd4/rgHuVnm9o0+iavasJSOm+eCfC/JWDKjkL8GmQDLFIIuj8Em2Tntp8AWZr0ZOIIFTF/biphfOSVmlrq6oQcjz0kMoLxTMGpNUkhinThOBY5rYXBeP1ewJj1j8nJF9mMZoLz12Krhpqwb2Mr3oFZZFG9m4MpkMs2C8CUl9IVQKBgQCvxE079L1VTasRzqV7Yki8SK8xdyZGhr9TO/5LF0BOkhKWZh15JyZ0Y4B7woBXURo1Fd0p76kk3BU5mOOoapE146eV/Yy02lBHKG0Wj49w2ifHsyZrXYG91j/CJ9oDz73ABasLGej1E8GZ4UrHUx3bAuZjDbOkcPokKSICTWVvPQKBgQC3wp96PiH7UZzq78aNRgz4lhHmEnCqEHtLPTr1Yd7XbkxRsM30Bdb+u/kq/JfwJiO8GUlZolhKAA0v4DOd6uFeyORCasJvw/oLyFNFUj5aWSvMyvsxusS3hvCiIaxOxoVhkEKjHQyETtN3pql+5huVSpQdNu8gCJLjF3ihVILmVQKBgQCuoeECwuUiTeq75I7yCfKub6DxCqrSw6pwLlk3jkBsUCF7oLWD2+wPE5ViYRtWpdPeDn0Q33c0kh3cfhUjDc58tlWZZrNYNNgx+u53zeb8fyOq/Eodvmfbj3UFmMa5prumf3szqmiUpy5hjz/WzrQJF9aDb+eeaEl/iAasNu2jGQKBgE5wZa9A659hA5UTcSMh8O7mWH2kgX7AjcVpd8++TYGw4dWFbiPwEPKUfbCTdKkGaFc3sI5OyyB73qkabIMHNL1ocHvAAwxU7E1gGnIgZD/YyWt1pPmPbi9jWCwqurcYeghbvK9T3dbS8ZA0ZuiFJYLlJCzbU6fQRtmynVhotesS";
    private static final String CHARSET = "UTF-8";
    // 支付宝公钥
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgxEDcc4IbL9qHpNdeULlsyW14WlAaPBpZzoxwmv/3CII1NdfceTTTK0l504MpUF3Ux2Diq3uKgt9tdjIHwgspKC8Jvg7EOYe1DPtVuHgin8x2BoTjXLKr5x5pDapJOqIfV2b6bj844UQ2DK8BHl2rDSEFG5Mt5S2MelZypOpT/dVCNrUGXAmsAlQRB+RcvTeDqptifWvU9DxQ8B8OXPRBe1fuEpFvPc1CEZ8vBqYmD/p1f3Cb4PDM9cBBe/xnw42qPxKbOAyXyUWER7rQLvsb9ORVPT9uB2Q2FZ4ZiUrWVQpwYopoE06EZdwh+iMz03uEspPdqBrSMHN5NPg1ewA0wIDAQAB";
    private static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    // 签名方式
    private static final String SIGN_TYPE = "RSA2";
    // 支付宝一步通知路径，公网地址
    private static final String NOTIFY_URL = "";
    // 支付宝同步通知路径，也就是当付款完毕后跳转本项目的页面，可私网地址
    private static final String RETURN_URL = "http://125.74.199.190:9902/product/paySuccess";

    /**
     * 处理请求支付的方法
     * @param session 建立HTTP回话
     * @param model 组件
     * @param donaMoney 商品价格
     * @param donaId 商品id
     * @param subject 商品名称
     * @return 返回请求成功后的商品信息
     * @throws AlipayApiException 支付API的内置异常处理
     */
    @GetMapping("/alipay")
    public String alipay(HttpSession session, Model model, @RequestParam(value = "dona_money") float donaMoney,
                         @RequestParam(value = "dona_id") int donaId,
                         @RequestParam(value = "dona_name") String subject) throws AlipayApiException {
        // 把项目id放在session中
        session.setAttribute("dona_id",donaId);

        // 生成订单号（支付宝的要求）
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String user = UUID.randomUUID().toString().replace("-", "").toUpperCase();

        String orderNum = time+user;
        System.out.println("订单号："+orderNum);

        // 调用封装好的方法（给支付宝接口发送请求）
        return sendRequestToAlipay(orderNum,donaMoney,subject);
    }

    /**
     * 请求成功后返回商品结果
     * @param outTradeNo 订单号
     * @param totalAmount 商品价格
     * @param subject 商品名称
     * @return 返回结果
     * @throws AlipayApiException 支付API的内置异常处理
     */
    private String sendRequestToAlipay(String outTradeNo,Float totalAmount,String subject) throws AlipayApiException {
        // 获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL,APP_ID,APP_PRIVATE_KET,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setNotifyUrl(NOTIFY_URL); // 支付宝通知的本地地址
        alipayRequest.setReturnUrl(RETURN_URL); // 支付宝异步回调的公网地址

        // 商品描述
        String body = "";
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\","
                        + "\"total_amount\":\"" + totalAmount + "\","
                        + "\"subject\":\"" + subject + "\","
                        + "\"body\":\"" + body + "\","
                        + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        // 请求
        String result =  alipayClient.pageExecute(alipayRequest).getBody();
        System.out.println("商品信息结果："+result);
        return result;
    }

    /**
     * 该方法可以获取支付成功后,支付宝回调时传递的信息
     * @param request HttpServletRequest
     * @param session 回话
     * @param model model
     * @return 返回页面信息
     * @throws UnsupportedEncodingException 不支持的转码异常
     * @throws AlipayApiException  支付时发生的异常
     */
    @RequestMapping("/returnUrl")
    public String returnUrlMethod(HttpServletRequest request,HttpSession session,Model model) throws UnsupportedEncodingException, AlipayApiException {
        log.debug("执行同步回调...");
        // 获取支付宝GET请求过来的反馈信息
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap(); // 获取get参数列表
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 解决乱码
            valueStr = new String(valueStr.getBytes("ISO_8859_1"), "utf-8");
            params.put(name, valueStr);
        }

        log.debug("支付宝传递过来的参数：{}",params);
        // 验证签名(支付宝公钥)
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);
        //验证签名通过
        if(signVerified){
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO_8859_1"), "UTF-8");

            // 支付宝交易流水号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO_8859_1"), "UTF-8");

            // 付款金额
            float money = Float.parseFloat(new String(request.getParameter("total_amount").getBytes("ISO_8859_1"), "UTF-8"));

            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("付款金额="+money);

            //在这里编写自己的业务代码（对数据库的操作）
			/*
			################################
			*/
            //跳转到提示页面（成功或者失败的提示页面）
            model.addAttribute("flag",1);
            model.addAttribute("msg","支持");
            return "common/payTips";
        }else{
            //跳转到提示页面（成功或者失败的提示页面）
            model.addAttribute("flag",0);
            model.addAttribute("msg","支持");
            return "common/payTips";
        }
    }

}
