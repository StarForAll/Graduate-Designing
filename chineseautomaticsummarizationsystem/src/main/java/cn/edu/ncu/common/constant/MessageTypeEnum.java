package cn.edu.ncu.common.constant;


import cn.edu.ncu.common.core.pojo.BaseEnum;

/**
 * @Author: XiongZhiCong
 * @Description: 通知类型枚举类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public enum MessageTypeEnum implements BaseEnum {

    SYS_NOTICE(1,"系统通知"),

    PRIVATE_LETTER(2,"私信"),

    HEART_BEAT(3,"心跳");


    private Integer value;

    private String desc;


    MessageTypeEnum(Integer value, String desc){
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
}
