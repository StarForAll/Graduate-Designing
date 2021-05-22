package cn.edu.ncu.common.constant;

/**
 * @Author: XiongZhiCong
 * @Description: 系统设置响应常量
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class SystemConfigResponseCodeConst extends ResponseCodeConst {

    /**
     * 配置参数已存在 10201
     */
    public static final SystemConfigResponseCodeConst ALREADY_EXIST = new SystemConfigResponseCodeConst(5001, "配置参数已存在");
     /**
     * 配置参数不存在 10203
     */
    public static final SystemConfigResponseCodeConst NOT_EXIST = new SystemConfigResponseCodeConst(5002, "配置参数不存在");

    public SystemConfigResponseCodeConst(int code, String msg) {
        super(code, msg);
    }
}
