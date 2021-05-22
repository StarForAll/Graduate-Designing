package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.BusinessErrorCode;
import cn.edu.ncu.common.constant.FileResponseCodeConst;
import cn.edu.ncu.common.constant.FileServiceNameConst;
import cn.edu.ncu.common.constant.SystemConfigEnum;
import cn.edu.ncu.common.core.exception.BusinessException;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.OSSConfig;
import cn.edu.ncu.pojo.vo.UploadVO;
import cn.edu.ncu.service.FileServiceAliYun;
import cn.edu.ncu.service.SystemConfigService;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: XiongZhiCong
 * @Description: 阿里云文件上传业务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Slf4j
@Service(FileServiceNameConst.ALI_OSS)
public class FileServiceAliYunImpl implements IFileService, FileServiceAliYun {

    @Autowired
    private SystemConfigService systemConfigService;

    OSSClient ossClient = null;

    String accessConfig = null;
    private OSSConfig getNewOSSConfig(){
        OSSConfig ossConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.ALI_OSS.name(), OSSConfig.class);
        if (! ossConfig.toString().equals(accessConfig)) {
            //accessKeyId 发生变动自动创建新的
            if (ossClient != null) {
                ossClient.shutdown();
            }
            ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
            accessConfig = ossConfig.toString();
        }
        return ossConfig;
    }
    @Override
    public ResponseDTO<UploadVO> fileUpload(MultipartFile multipartFile, String path) {
        OSSConfig ossConfig = getNewOSSConfig();
        try {
            InputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes());
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String ossPath = path + "/" + uuid;
            String fileName = multipartFile.getOriginalFilename();
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8));
            ObjectMetadata objectMetadata=new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            objectMetadata.setObjectAcl(CannedAccessControlList.PublicRead);
            ossClient.putObject(ossConfig.getBucketName(),ossPath,inputStream, objectMetadata);

//            ObjectMetadata meta = new ObjectMetadata();
//            meta.setContentDisposition("attachment;filename=" + fileName);
//            Map<String, String> userMetadata = new HashMap();
//            userMetadata.put("fileName", fileName);
//            userMetadata.put("fileExt", fileExt);
//            userMetadata.put("fileSize", String.valueOf(multipartFile.getSize()));
//            meta.setUserMetadata(userMetadata);
//            meta.setContentType(this.getContentType(fileExt));
//            PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucketName(), ossPath, inputStream, meta);
//            ossClient.putObject(putObjectRequest);
            UploadVO localUploadVO = new UploadVO();
            localUploadVO.setUrl(this.getUrl(ossPath, ossConfig.getBucketName(), ossClient));
            localUploadVO.setFileName(fileName);
            localUploadVO.setFilePath(ossPath);
            localUploadVO.setFileSize(multipartFile.getSize());
            return ResponseDTO.succData(localUploadVO);
        } catch (Exception e) {
            log.error("ALI UPLOAD ERROR : {}", e);
        }
        return ResponseDTO.wrap(FileResponseCodeConst.UPLOAD_ERROR);
    }
    /**
     * 根据文件的url进行删除操作
     * @param fileUrl
     */
    @Override
    public void deleteFileOSS(String fileUrl) {
        OSSConfig ossConfig = getNewOSSConfig();
        try {
            //----------------需要进行验证该代码
            ossClient.deleteObject(ossConfig.getBucketName(), fileUrl);
        }catch (Exception e) {
            //统一异常：图片上传失败
            throw new BusinessException(BusinessErrorCode.SYSTEM_ERROR_CDN,e);
        }
    }
    @Override
    public ResponseDTO<String> getFileUrl(String path) {
        OSSConfig ossConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.ALI_OSS.name(), OSSConfig.class);
        try {
            if (! ossConfig.toString().equals(accessConfig)) {
                //accessKeyId 发生变动自动创建新的
                if (ossClient != null) {
                    ossClient.shutdown();
                }
                ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
                accessConfig = ossConfig.toString();
            }
            String url = this.getUrl(path, ossConfig.getBucketName(), ossClient);
            return ResponseDTO.succData(url);
        } catch (Exception e) {
            log.error("ALI getFileUrl ERROR : {}", e);
        }
        return ResponseDTO.wrap(FileResponseCodeConst.URL_ERROR);
    }

    private String getUrl(String path, String bucketName, OSSClient ossClient) {
        Date expiration = new Date(System.currentTimeMillis() + (60 * 60 * 1000));
        URL url = ossClient.generatePresignedUrl(bucketName, path, expiration);
        return url.toString();
    }

    /**
     * 流式下载（名称为原文件）
     */
    @Override
    public ResponseEntity<byte[]> fileDownload(String key, String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
        File file = this.getFile(key, fileName);
        if (file == null) {
            throw new RuntimeException("文件不存在");
        }
        return this.downloadMethod(file, request);
    }

    /**
     * 根据osskey获取文件
     *
     * @param key
     * @return
     */
    @Override
    public File getFile(String key, String fileName) {
        OSSConfig ossConfig = getNewOSSConfig();
        //获取oss对象
        OSSObject ossObject = ossClient.getObject(ossConfig.getBucketName(), key);
        if (StringUtils.isBlank(fileName)) {
            // 获取元信息
            ObjectMetadata objectMetadata = ossObject.getObjectMetadata();
            // 获取下载时文件名
            Map<String, String> userMetadata = objectMetadata.getUserMetadata();
            fileName = userMetadata == null ? "" : userMetadata.get("filename");
            if (StringUtils.isBlank(fileName)) {
                fileName = objectMetadata.getContentDisposition();
            }
        }
        // 创建文件
        File file = new File(fileName);
        // 获得输入流
        InputStream objectContent = ossObject.getObjectContent();
        try {
            // 输入流转换为字节流
            byte[] buffer = FileCopyUtils.copyToByteArray(objectContent);
            // 字节流写入文件
            FileCopyUtils.copy(buffer, file);
            // 关闭输入流
            objectContent.close();
        } catch (IOException e) {
            log.error("文件获取失败：" + e);
            return null;
        } finally {
            try {
                ossObject.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return file;
    }
}
