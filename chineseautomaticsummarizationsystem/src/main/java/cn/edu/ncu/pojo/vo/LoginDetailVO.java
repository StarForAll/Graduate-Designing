package cn.edu.ncu.pojo.vo;

import cn.edu.ncu.pojo.dto.LoginPrivilegeDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 登录返回VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class LoginDetailVO {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("登录账号")
    private String loginName;

    @ApiModelProperty("别名")
    private String nickName;

    @ApiModelProperty("员工名称")
    private String actualName;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("身份证")
    private String idCard;

    @ApiModelProperty("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    @ApiModelProperty("创建者id")
    private Long createUser;

    @ApiModelProperty("部门id")
    private Long departmentId;

    @ApiModelProperty("是否离职")
    private Integer isLeave;

    @ApiModelProperty("是否被禁用")
    private Integer isDisabled;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("登陆token")
    private String xAccessToken;

    @ApiModelProperty("是否为超管")
    private Boolean isSuperMan;

    @ApiModelProperty("权限列表")
        private List<LoginPrivilegeDTO> privilegeList;

}
