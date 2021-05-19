package cn.edu.ncu.common.core.pojo.dto;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: DTO 从这里继承便于统一DTO接口和转型判断
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDTO implements Serializable {
    private static final long serialVersionUID = -4183652945764463929L;

    private Long id;

    @Override
    public String toString() {
       return JSON.toJSONString(this);
    }
}
