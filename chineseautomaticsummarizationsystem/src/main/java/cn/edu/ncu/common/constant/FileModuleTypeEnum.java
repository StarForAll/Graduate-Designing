package cn.edu.ncu.common.constant;


import cn.edu.ncu.common.core.pojo.BaseEnum;

/**
 * @Author: XiongZhiCong
 * @Description: 文件业务类型枚举类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public enum FileModuleTypeEnum implements BaseEnum {

    /**
     * path 首字符不能包含\ 或者/
     */

    BACK_USER(1, "backUser/config", "backUser"),

    CODE_REVIEW(2, "codeReview", "CodeReview"),
    TEXT_SUMMARY(3,"summary","自动摘要文件夹");

    private Integer value;

    private String path;

    private String desc;

    FileModuleTypeEnum(Integer value, String path, String desc) {
        this.value = value;
        this.path = path;
        this.desc = desc;
    }

    public String getPath() {
        return path;
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
