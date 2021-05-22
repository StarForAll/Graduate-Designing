package cn.edu.ncu.service;

import java.io.File;

/**
 * @Author: XiongZhiCong
 * @Description: OSS文件上传
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface FileServiceAliYun{
    /**
     * 根据osskey获取文件
     *
     * @param key
     * @return
     */
     File getFile(String key, String fileName);
     void deleteFileOSS(String fileUrl);
}
