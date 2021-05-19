package cn.edu.ncu.common.core.pojo.vo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseVO implements Serializable {

    private static final long serialVersionUID = -2361201137582920579L;

    private Long id;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
