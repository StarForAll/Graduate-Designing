package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: XiongZhiCong
 * @Description: 邮件实体
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name="email")
public class Email extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 收件人
     */
    private String toEmails;

    /**
     * 发送状态 0未发送 1已发送
     */
    private Integer sendStatus;

    /**
     * 邮件内容
     */
    private String content;

}
