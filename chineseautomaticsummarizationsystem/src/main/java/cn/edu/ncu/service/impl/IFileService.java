package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.vo.UploadVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @Author: XiongZhiCong
 * @Description: 文件服务接口
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface IFileService {

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param path
     * @return
     */
    ResponseDTO<UploadVO> fileUpload(MultipartFile multipartFile, String path);

    /**
     * 获取文件url
     *
     * @param path
     * @return
     */
    ResponseDTO<String> getFileUrl(String path);

    /**
     * 文件下载
     *
     * @param key
     * @param fileName
     * @param request
     * @return
     */
    ResponseEntity<byte[]> fileDownload(String key, String fileName, HttpServletRequest request) throws UnsupportedEncodingException;

    /**
     * 生成文件名字
     * 当前年月日时分秒 +32位 uuid + 文件格式后缀
     *
     * @param originalFileName
     * @return String
     */
    default String generateFileName(String originalFileName) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmms"));
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
        return time + uuid + fileType;
    }

    /**
     * 获取文件类型
     *
     * @param fileExt
     * @return
     */
    default String getContentType(String fileExt) {
        // 文件的后缀名
        if ("bmp".equalsIgnoreCase(fileExt)) {
            return "image/bmp";
        }
        if ("gif".equalsIgnoreCase(fileExt)) {
            return "image/gif";
        }
        if ("jpeg".equalsIgnoreCase(fileExt) || "jpg".equalsIgnoreCase(fileExt) || ".png".equalsIgnoreCase(fileExt)) {
            return "image/jpeg";
        }
        if ("png".equalsIgnoreCase(fileExt)) {
            return "image/png";
        }
        if ("html".equalsIgnoreCase(fileExt)) {
            return "text/html";
        }
        if ("txt".equalsIgnoreCase(fileExt)) {
            return "text/plain";
        }
        if ("vsd".equalsIgnoreCase(fileExt)) {
            return "application/vnd.visio";
        }
        if ("ppt".equalsIgnoreCase(fileExt) || "pptx".equalsIgnoreCase(fileExt)) {
            return "application/vnd.ms-powerpoint";
        }
        if ("doc".equalsIgnoreCase(fileExt) || "docx".equalsIgnoreCase(fileExt)) {
            return "application/msword";
        }
        if ("xml".equalsIgnoreCase(fileExt)) {
            return "text/xml";
        }
        return "";
    }

    default ResponseEntity<byte[]> downloadMethod(File file, HttpServletRequest request) throws UnsupportedEncodingException {

        HttpHeaders heads = new HttpHeaders();
        heads.add(HttpHeaders.CONTENT_TYPE, "multipart/form-data");
        String fileName = URLEncoder.encode(file.getName(), "UTF-8");
        heads.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
        try {
            InputStream in = new FileInputStream(file);
            // 输入流转换为字节流
            byte[] buffer = FileCopyUtils.copyToByteArray(in);
            in.close();
            //file.delete();
            return new ResponseEntity<>(buffer, heads, HttpStatus.OK);
        } catch (Exception e) {
            // log.error("", e);
        }
        return null;
    }
}
