package cn.edu.ncu.common.core.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @class DropdownVO
 * @classdesc 由其作用得名，专为下拉框设计，常用于 id -> name
 * @author liang_xiaojian
 * @date 2020/9/24  23:36
 * @version 1.0.0
 * @see
 * @since
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DropdownVO implements Serializable {
    private static final long serialVersionUID = -1095151363864965465L;

    private Long id;
    private String value;
}
