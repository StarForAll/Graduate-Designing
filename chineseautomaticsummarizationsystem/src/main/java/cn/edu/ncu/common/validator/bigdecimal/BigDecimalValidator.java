package cn.edu.ncu.common.validator.bigdecimal;


import cn.edu.ncu.common.util.basic.BigDecimalUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * @Author: XiongZhiCong
 * @Description: BigDecimal 类校验器
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class BigDecimalValidator implements ConstraintValidator<CheckBigDecimal, BigDecimal> {

    /**
     * 获取定义的数值
     */
    private BigDecimal value;

    /**
     * 获取比较符
     */
    private ComparisonSymbolEnum symbolEnum;

    /**
     * 是否必须
     */
    private boolean required;

    @Override
    public void initialize(CheckBigDecimal constraintAnnotation) {
        // 初始化属性
        value = new BigDecimal(constraintAnnotation.value());
        symbolEnum = constraintAnnotation.symbolEnum();
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(BigDecimal decimal, ConstraintValidatorContext constraintValidatorContext) {

        // 如果数值为空，校验是否必须
        if (null == decimal) {
            return ! required;
        }

        // 根据操作符，校验结果
        switch (symbolEnum) {
            // 等于
            case EQUAL:
                return BigDecimalUtil.equals(decimal, value);
            // 不等于
            case NOT_EQUAL:
                return ! BigDecimalUtil.equals(decimal, value);
            // 小于
            case LESS_THAN:
                return BigDecimalUtil.isLessThan(decimal, value);
            // 小于等于
            case LESS_THAN_OR_EQUAL:
                return BigDecimalUtil.isLessThan(decimal, value) || BigDecimalUtil.equals(decimal, value);
            // 大于
            case GREATER_THAN:
                return BigDecimalUtil.isGreaterThan(decimal, value);
            // 大于等于
            case GREATER_THAN_OR_EQUAL:
                return BigDecimalUtil.isGreaterThan(decimal, value) || BigDecimalUtil.equals(decimal, value);
            default:
        }

        return false;
    }
}
