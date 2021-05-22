package cn.edu.ncu.common.constant;


import cn.edu.ncu.common.core.pojo.BaseEnum;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: XiongZhiCong
 * @Description: [ 是与否]
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public enum JudgeEnum implements BaseEnum {

    NO(0, "否"),

    YES(1, "是");

    private Integer value;
    private String desc;

    JudgeEnum(Integer value, String desc) {
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

    public static JudgeEnum valueOf(Integer status) {
        JudgeEnum[] values = JudgeEnum.values();
        Optional<JudgeEnum> first = Arrays.stream(values).filter(e -> e.getValue().equals(status)).findFirst();
        return !first.isPresent() ? null : first.get();
    }

    public static boolean isExist(Integer status) {
        JudgeEnum judgeEnum = valueOf(status);
        return judgeEnum != null;
    }
}
