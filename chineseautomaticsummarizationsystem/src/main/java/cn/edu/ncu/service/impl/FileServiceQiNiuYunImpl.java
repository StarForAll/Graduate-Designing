package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.FileResponseCodeConst;
import cn.edu.ncu.common.constant.FileServiceNameConst;
import cn.edu.ncu.common.constant.SystemConfigEnum;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.OSSConfig;
import cn.edu.ncu.pojo.vo.UploadVO;
import cn.edu.ncu.service.FileServiceQiNiuYun;
import cn.edu.ncu.service.SystemConfigService;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @Author: XiongZhiCong
 * @Description: 七牛云文件上传
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Slf4j
@Service(FileServiceNameConst.QI_NIU_OSS)
public class FileServiceQiNiuYunImpl implements IFileService, FileServiceQiNiuYun {

    /**
     * 1小时，可以自定义链接过期时间
     */
    private static final Long EXPIRE_IN_SECONDS = 3600L;

    @Autowired
    private SystemConfigService systemConfigService;

    UploadManager ossClient = null;

    String accessConfig = null;

    String token = null;

    @Override
    public ResponseDTO<UploadVO> fileUpload(MultipartFile multipartFile, String path) {
        OSSConfig ossConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.QI_NIU_OSS.name(), OSSConfig.class);
        try {
            InputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes());
            if (! ossConfig.toString().equals(accessConfig)) {
                //accessKeyId 发生变动自动重新创建新的UploadManager
                ossClient = new UploadManager(new Configuration());
                token = Auth.create(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret()).
                    uploadToken(ossConfig.getBucketName());
                accessConfig = ossConfig.toString();
            }
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String ossPath = path + "/" + uuid;
            String fileName = multipartFile.getOriginalFilename();
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
            String mime = this.getContentType(fileExt);
            Response res = ossClient.put(inputStream, ossPath, token, null, mime);
            if (! res.isOK()) {
                log.error("QINIU fileUpload ERROR : {}", res.toString());
                return ResponseDTO.wrap(FileResponseCodeConst.UPLOAD_ERROR);
            }
            UploadVO localUploadVO = new UploadVO();
            localUploadVO.setUrl(this.getFileUrl(ossPath).getData());
            localUploadVO.setFileName(fileName);
            localUploadVO.setFilePath(ossPath);
            localUploadVO.setFileSize(multipartFile.getSize());
            return ResponseDTO.succData(localUploadVO);
        } catch (Exception e) {
            log.error("QINIU fileUpload ERROR : {}", e);
        }
        return ResponseDTO.wrap(FileResponseCodeConst.UPLOAD_ERROR);
    }

    @Override
    public ResponseDTO<String> getFileUrl(String path) {
        OSSConfig ossConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.QI_NIU_OSS.name(), OSSConfig.class);
        try {
            if (! ossConfig.toString().equals(accessConfig)) {
                //accessKeyId 发生变动自动重新创建新的UploadManager
                ossClient = new UploadManager(new Configuration());
                token = Auth.create(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret()).
                    uploadToken(ossConfig.getBucketName());
                accessConfig = ossConfig.toString();
            }
            String encodedFileName = URLEncoder.encode(path, "utf-8");
            String domainOfBucket = ossConfig.getEndpoint();
            String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
            String accessKey = ossConfig.getAccessKeyId();
            String secretKey = ossConfig.getAccessKeySecret();
            Auth auth = Auth.create(accessKey, secretKey);
            //1小时，可以自定义链接过期时间
            long EXPIRE_IN_SECONDS = 3600;
            String finalUrl = auth.privateDownloadUrl(publicUrl, EXPIRE_IN_SECONDS);
            return ResponseDTO.succData(finalUrl);
        } catch (Exception e) {
            log.error("QINIU getFileUrl ERROR : {}", e);
        }
        return ResponseDTO.wrap(FileResponseCodeConst.URL_ERROR);
    }

    @Override
    public ResponseEntity<byte[]> fileDownload(String key, String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
        File file = this.getFile(key, fileName);
        if (file == null) {
            throw new RuntimeException("文件不存在");
        }
        return this.downloadMethod(file, request);
    }

    /**
     * 获取下载路径
     */
    @Override
    public String getDownloadUrl(String key) {
        OSSConfig ossConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.QI_NIU_OSS.name(), OSSConfig.class);
        String domainOfBucket = ossConfig.getEndpoint();
        String finalUrl = "";
        try {
            String encodedFileName = URLEncoder.encode(key, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
            Auth auth = Auth.create(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
            finalUrl = auth.privateDownloadUrl(publicUrl, EXPIRE_IN_SECONDS);
        } catch (Exception e) {
            log.error("QINIU download ERROR : {}", e);
        }
        return finalUrl;
    }

    /**
     * 获取文件
     */
    @Override
    public File getFile(String key, String fileName) {
        String finalUrl = getDownloadUrl(key);
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(finalUrl).build();
        okhttp3.Response resp = null;
        File file = new File(fileName);
        try {
            resp = client.newCall(req).execute();
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream objectContent = body.byteStream();
                // 输入流转换为字节流
                byte[] buffer = FileCopyUtils.copyToByteArray(objectContent);
                // 字节流写入文件
                FileCopyUtils.copy(buffer, file);
                // 关闭输入流
                objectContent.close();
            }

        } catch (IOException e) {
            log.error("文件获取失败：" + e);
            return null;
        } finally {
        }
        return file;
    }
}
