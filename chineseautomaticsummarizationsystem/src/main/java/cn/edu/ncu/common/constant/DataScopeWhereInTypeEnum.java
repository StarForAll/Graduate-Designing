package cn.edu.ncu.common.constant;

import cn.edu.ncu.common.core.pojo.BaseEnum;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 14:16 2021/4/8
 * @Modified By:
 */
public enum DataScopeWhereInTypeEnum implements BaseEnum {

    EMPLOYEE(0,"以员工IN"),

    DEPARTMENT(1,"以部门IN"),

    CUSTOM_STRATEGY(2,"自定义策略");

    private Integer value;
    private String desc;

    DataScopeWhereInTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }


}
