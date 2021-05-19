package cn.edu.ncu.common.core.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.Version;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: 系统表包含的公用字段进行提取，统一切面也将使用该类型
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 2611297623998329330L;
    @Id
    private Long id;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;
}
