package cn.edu.ncu.common.config.swagger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * @Author: XiongZhiCong
 * @Description: swagger参数类
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    /**
     * API 文档生成基础路径
     */
    private String apiBasePackage;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 文档版本
     */
    private String version;
    /**
     * 文档联系人姓名
     */
    private String contactName;
    /**
     * 文档联系人网址
     */
    private String contactUrl;
    /**
     * 文档联系人邮箱
     */
    private String contactEmail;

}
