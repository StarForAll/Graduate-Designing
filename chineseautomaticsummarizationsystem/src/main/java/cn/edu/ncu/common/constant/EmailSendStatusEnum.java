package cn.edu.ncu.common.constant;


/**
 * @Author: XiongZhiCong
 * @Description: 邮件发送常量
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public enum EmailSendStatusEnum {

    NOT_SEND(0,"未发送"),

    SEND(1,"已发送");

    private Integer type;
    private String desc;

    EmailSendStatusEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


}
