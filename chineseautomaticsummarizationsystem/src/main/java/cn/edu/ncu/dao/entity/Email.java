package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019-05-13 17:10:16
 * @since JDK1.8
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
