package cn.edu.ncu.pojo.vo;

import lombok.Data;
/**
 * @Author: XiongZhiCong
 * @Description: 验证码VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class KaptchaVO {

    /**
     *  验证码UUID
     */
    private String uuid;

    /**
     * base64 验证码
     */
    private String code;

}
