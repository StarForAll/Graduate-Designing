package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: XiongZhiCong
 * @Description: 通知接收记录实体类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name="notice_receive_record")
public class NoticeReceiveRecord extends BaseEntity {


    /**
     * 消息id
     */
    private Long noticeId;

    /**
     * 消息接收人
     */
    private Long employeeId;



}
