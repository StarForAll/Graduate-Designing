/**
 * @file:  DropdownDTO.java
 * @author: liang_xiaojian
 * @date:   2020/9/24 23:36
 * @copyright: 2020-2023 www.bosssoft.com.cn Inc. All rights reserved.
 */
package cn.edu.ncu.common.core.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: XiongZhiCong
 * @Description: 由其作用得名，专为下拉框设计，常用于 id -> name
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DropdownDTO implements Serializable {
    private static final long serialVersionUID = -1095151363864965465L;
    /**
     *  下拉框数据ID
     */
    private Long id;
    /**
     *  下拉框的item value
     */
    private String value;
}
