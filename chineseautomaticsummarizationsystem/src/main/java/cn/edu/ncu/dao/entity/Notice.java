package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: XiongZhiCong
 * @Description: 通知
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name="notice")
public class Notice extends BaseEntity {

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息创建人
     */
    private Long createUser;

    /**
     * 发送状态
     */
    private Integer sendStatus;

    /**
     * 删除状态
     */
    private Integer deleted;

}
