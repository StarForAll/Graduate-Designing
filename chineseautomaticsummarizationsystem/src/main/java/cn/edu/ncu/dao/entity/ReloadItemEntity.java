package cn.edu.ncu.dao.entity;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: t_reload_item 数据表 实体类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name="reload_item")
public class ReloadItemEntity {

    /**
     * 加载项标签
     */
    @Id
    private String tag;

    /**
     * 参数
     */
    private String args;

    /**
     * 运行标识
     */
    private String identification;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;


}
