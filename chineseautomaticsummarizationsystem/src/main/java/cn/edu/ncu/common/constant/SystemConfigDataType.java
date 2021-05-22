package cn.edu.ncu.common.constant;


import cn.edu.ncu.common.util.basic.VerificationUtil;

/**
 * @Author: XiongZhiCong
 * @Description: 系统设置数据类型常量
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public enum SystemConfigDataType {
    /**
     * 整数
     */
    INTEGER(VerificationUtil.INTEGER),
    /**
     * 文本
     */
    TEXT(null),
    /**
     * url地址
     */
    URL(VerificationUtil.URL),
    /**
     *  邮箱
     */
    EMAIL(VerificationUtil.EMAIL),
    /**
     * JSON 字符串
     */
    JSON(null),
    /**
     * 2019-08
     */
    YEAR_MONTH(VerificationUtil.YEAR_MONTH),
    /**
     * 2019-08-01
     */
    DATE(VerificationUtil.DATE),
    /**
     * 2019-08-01 10:23
     */
    DATE_TIME(VerificationUtil.DATE_TIME),
    /**
     * 10:23-10:56
     */
    TIME_SECTION(VerificationUtil.TIME_SECTION),
    /**
     * 10:23
     */
    TIME(VerificationUtil.TIME);

    private String valid;


    SystemConfigDataType(String valid){
        this.valid = valid;
    }

    public String getValid() {
        return valid;
    }
}
