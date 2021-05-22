package cn.edu.ncu.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 上传VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class UploadVO {

    @ApiModelProperty(value = "文件名称")
    private String fileName;
    @ApiModelProperty(value = "url")
    private String url;
    @ApiModelProperty(value = "filePath")
    private String filePath;
    @ApiModelProperty(value = "文件大小")
    private Long fileSize;
}
