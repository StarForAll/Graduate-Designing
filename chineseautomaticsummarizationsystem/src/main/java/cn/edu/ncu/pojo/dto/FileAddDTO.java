package cn.edu.ncu.pojo.dto;

import cn.edu.ncu.common.constant.FileServiceTypeEnum;
import cn.edu.ncu.common.validator.en.CheckEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: XiongZhiCong
 * @Description: 文件保存DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class FileAddDTO {

    @ApiModelProperty("相关业务id(无业务可写死一个id)")
    @NotBlank(message = "相关业务id不能为空")
    private String moduleId;

    @ApiModelProperty("相关业务类型(无模块写1)")
    @NotBlank(message = "相关业务类型不能为空")
    private String moduleType;

    @CheckEnum(enumClazz = FileServiceTypeEnum.class,message = "文件类型错误")
    private Integer fileLocationType;

    @ApiModelProperty("文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    @ApiModelProperty("文件路径")
    @NotBlank(message = "文件路径不能为空")
    private String filePath;

}
