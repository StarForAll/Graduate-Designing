package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.FileResponseCodeConst;
import cn.edu.ncu.common.constant.FileServiceNameConst;
import cn.edu.ncu.common.constant.SystemConfigEnum;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.dao.entity.SystemConfig;
import cn.edu.ncu.dao.mapper.SystemConfigMapper;
import cn.edu.ncu.pojo.vo.UploadVO;
import cn.edu.ncu.service.FileServiceLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Author: XiongZhiCong
 * @Description: 本地文件业务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Slf4j
@Service(FileServiceNameConst.LOCAL)
public class FileServiceLocalImpl implements IFileService, FileServiceLocal {

    @Resource
    private SystemConfigMapper systemConfigMapper;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Value("${file-upload-service.path}")
    private String fileParentPath;

    private static final Long DEFAULT_SIZE = 10 * 1024 * 1024L;

    @Override
    public ResponseDTO<UploadVO> fileUpload(MultipartFile multipartFile, String path) {
        if (null == multipartFile) {
            return ResponseDTO.wrap(FileResponseCodeConst.FILE_EMPTY);
        }
        Long maxSize = DEFAULT_SIZE;
        if (StringUtils.isNotEmpty(maxFileSize)) {
            String maxSizeStr = maxFileSize.toLowerCase().replace("mb", "");
            maxSize = Integer.valueOf(maxSizeStr) * 1024 * 1024L;
        }
        if (multipartFile.getSize() > maxSize) {
            return ResponseDTO.wrap(FileResponseCodeConst.FILE_SIZE_ERROR, String.format(FileResponseCodeConst.FILE_SIZE_ERROR.getMsg(), maxFileSize));
        }
        String filePath = fileParentPath;
        String urlParent = this.localUrlPrefix();
        if (urlParent == null) {
            return ResponseDTO.wrap(FileResponseCodeConst.LOCAL_UPDATE_PREFIX_ERROR);
        }
        if (StringUtils.isNotEmpty(path)) {
            filePath = filePath + path + "/";
            urlParent = urlParent + path + "/";
        }
        File directory = new File(filePath);
        if (!directory.exists()) {
            // 目录不存在，新建
            directory.mkdirs();
        }
        UploadVO localUploadVO = new UploadVO();
        File fileTemp;
        String originalFileName = multipartFile.getOriginalFilename();
        fileTemp = new File(new File(filePath + originalFileName).getAbsolutePath());
        try {
            multipartFile.transferTo(fileTemp);
            localUploadVO.setUrl(urlParent + originalFileName);
            localUploadVO.setFileName(originalFileName);
            localUploadVO.setFilePath(path + "/" + originalFileName);
            localUploadVO.setFileSize(multipartFile.getSize());
        } catch (IOException e) {
            if (fileTemp.exists() && fileTemp.isFile()) {
                fileTemp.delete();
            }
            log.error("", e);
            return ResponseDTO.wrap(FileResponseCodeConst.UPLOAD_ERROR);
        }
        return ResponseDTO.succData(localUploadVO);
    }

    @Override
    public ResponseDTO<String> getFileUrl(String path) {
        String urlParent = this.localUrlPrefix();
        if (urlParent == null) {
            return ResponseDTO.wrap(FileResponseCodeConst.LOCAL_UPDATE_PREFIX_ERROR);
        }
        String url = urlParent + path;
        return ResponseDTO.succData(url);
    }

    private String localUrlPrefix() {
        SystemConfig configEntity = systemConfigMapper.getByKey(SystemConfigEnum.Key.LOCAL_UPLOAD_URL_PREFIX.name());
        if (configEntity == null) {
            return null;
        }
        return configEntity.getConfigValue();
    }

    @Override
    public ResponseEntity<byte[]> fileDownload(String key, String fileName, HttpServletRequest request) throws UnsupportedEncodingException {

        String url = fileParentPath + key;
        // 创建文件
        File file = new File(url);
        return this.downloadMethod(file, request);
    }
}
