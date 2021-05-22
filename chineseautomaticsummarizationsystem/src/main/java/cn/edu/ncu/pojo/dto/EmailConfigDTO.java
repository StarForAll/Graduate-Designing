package cn.edu.ncu.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: XiongZhiCong
 * @Description: 邮件设置DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfigDTO {

    private String smtpHost;

    private String username;

    private String password;

}
