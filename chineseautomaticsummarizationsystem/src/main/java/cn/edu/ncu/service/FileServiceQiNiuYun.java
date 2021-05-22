package cn.edu.ncu.service;

import java.io.File;

/**
 * @Author: XiongZhiCong
 * @Description: 文件七牛云上传
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface FileServiceQiNiuYun {





    /**
     * 获取下载路径
     */
     String getDownloadUrl(String key);

    /**
     * 获取文件
     */
     File getFile(String key, String fileName) ;
}
