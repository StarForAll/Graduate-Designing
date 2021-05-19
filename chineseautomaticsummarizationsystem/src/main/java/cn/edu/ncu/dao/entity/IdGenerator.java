package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author sun
 * @Auther: anders
 * @Date: 2018/8/7 0007 13:33
 * @Description:
 */
@Data
@Table(name= "id_generator")
public class IdGenerator extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5582354131134766548L;
    /**
     * 英文key
     */
    private String keyName;
    /**
     * 规则格式
     */
    private String ruleFormat;
    /**
     * 类型
     */
    private String ruleType;
    /**
     * 初始值
     */
    private Long initNumber;
    /**
     * 上次产生的id
     */
    private Long lastNumber;
    /**
     * 备注
     */
    private String remark;
}
