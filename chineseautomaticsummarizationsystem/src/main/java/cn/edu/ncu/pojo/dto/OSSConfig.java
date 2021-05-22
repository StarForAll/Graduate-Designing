package cn.edu.ncu.pojo.dto;

import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: OSS设置
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class OSSConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    @Override
    public String toString() {
        return "OSSConfig{" +
                "endpoint='" + endpoint + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", bucketName='" + bucketName + '\'' +
                '}';
    }
}
