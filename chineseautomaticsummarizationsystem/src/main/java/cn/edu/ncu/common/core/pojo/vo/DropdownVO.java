package cn.edu.ncu.common.core.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: XiongZhiCong
 * @Description: 由其作用得名，专为下拉框设计，常用于 id -> name
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DropdownVO implements Serializable {
    private static final long serialVersionUID = -1095151363864965465L;

    private Long id;
    private String value;
}
