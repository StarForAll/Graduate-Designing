package cn.edu.ncu.service;

import java.io.File;

/**
 * [ 七牛云 ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/5/10 0010 上午 8:30
 * @since JDK1.8
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
