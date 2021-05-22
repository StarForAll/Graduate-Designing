package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;


/**
 * @Author: XiongZhiCong
 * @Description: 用户登录日志
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_login_log")
public class UserLoginLog extends BaseEntity {

    /**
     * 员工id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户ip
     */
    private String remoteIp;

    /**
     * 用户端口
     */
    private Integer remotePort;

    /**
     * 浏览器
     */
    private String remoteBrowser;

    /**
     * 操作系统
     */
    private String remoteOs;

    /**
     * 登录状态
     */
    private Integer loginStatus;



}
