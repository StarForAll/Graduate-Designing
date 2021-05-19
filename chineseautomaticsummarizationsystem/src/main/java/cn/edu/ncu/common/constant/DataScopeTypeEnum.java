package cn.edu.ncu.common.constant;


import cn.edu.ncu.common.core.pojo.BaseEnum;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 14:16 2021/4/8
 * @Modified By:
 */
public enum DataScopeTypeEnum implements BaseEnum {

    DEFAULT(0,0,"默认类型","数据范围样例");

    private Integer value;
    private Integer sort;
    private String name;
    private String desc;

    DataScopeTypeEnum(Integer value,Integer sort,String name,String desc) {
        this.value = value;
        this.sort = sort;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public Integer getSort() {
        return sort;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }


}
