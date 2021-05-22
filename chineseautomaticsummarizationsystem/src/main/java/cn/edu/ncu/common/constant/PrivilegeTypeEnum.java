package cn.edu.ncu.common.constant;



import cn.edu.ncu.common.core.pojo.BaseEnum;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: XiongZhiCong
 * @Description: 权限类型枚举类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public enum PrivilegeTypeEnum implements BaseEnum {


    MENU(1,"菜单"),

    POINTS(2,"功能点");

    private Integer value;

    private String desc;

    PrivilegeTypeEnum(Integer value,String desc){
        this.value = value;
        this.desc = desc;
    }
    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    public static PrivilegeTypeEnum selectByValue(Integer value) {
        Optional<PrivilegeTypeEnum> first = Arrays.stream(PrivilegeTypeEnum.values()).filter(e -> e.getValue().equals(value)).findFirst();
        return !first.isPresent() ? null : first.get();
    }
}
