package cn.edu.ncu.service;

import java.io.File;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/5/10 0010 上午 8:29
 * @since JDK1.8
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
