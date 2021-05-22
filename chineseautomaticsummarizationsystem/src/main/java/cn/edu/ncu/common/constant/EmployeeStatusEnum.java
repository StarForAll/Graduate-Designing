package cn.edu.ncu.common.constant;


import cn.edu.ncu.common.core.pojo.BaseEnum;

/**
 * @Author: XiongZhiCong
 * @Description: 员工状态枚举类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */

public enum EmployeeStatusEnum implements BaseEnum {

    /**
     * 用户正常状态 1
     */
    NORMAL(0, "正常"),

    /**
     * 用户已被禁用 0
     */
    DISABLED(1, "禁用");

    private Integer value;

    private String desc;

    EmployeeStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取枚举类的值
     *
     * @return Integer
     */
    @Override
    public Integer getValue() {
        return value;
    }

    /**
     * 获取枚举类的说明
     *
     * @return String
     */
    @Override
    public String getDesc() {
        return this.desc;
    }
}