package cn.edu.ncu.common.constant;

/**
 * @Author: XiongZhiCong
 * @Description: 定时任务结果枚举类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public enum TaskResultEnum {

    SUCCESS(0,"成功"),
    /**
     *
     */
    FAIL(1,"失败");

    public static final String INFO="0:成功，1:失败";

    private Integer status;

    private String desc;

    TaskResultEnum(Integer status , String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
