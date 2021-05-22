package cn.edu.ncu.pojo.dto;

import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 登录缓存DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class LoginCacheDTO {

    /**
     * 基本信息
     */
    private EmployeeDTO employeeDTO;

    /**
     * 过期时间
     */
    private Long expireTime;
}
