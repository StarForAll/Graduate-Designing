package cn.edu.ncu.controller;

import cn.edu.ncu.api.FileApi;
import cn.edu.ncu.common.constant.FileServiceTypeEnum;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.util.web.RequestTokenUtil;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.dto.FileAddDTO;
import cn.edu.ncu.pojo.dto.FileQueryDTO;
import cn.edu.ncu.pojo.vo.FileVO;
import cn.edu.ncu.pojo.vo.UploadVO;
import cn.edu.ncu.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 文件服务
 * @Author: sbq
 * @CreateDate: 2019/7/18 9:36
 * @Version: 1.0
 */
@RestController
public class FileController implements FileApi {

    @Autowired
    private FileService fileService;
    @Override
    public ResponseDTO<UploadVO> localUpload(MultipartFile file, @PathVariable Integer moduleType) throws Exception {
        return fileService.fileUpload(file, FileServiceTypeEnum.LOCAL, moduleType);
    }
    @Override
    public ResponseDTO<String> localGetFile(String path) {
        return fileService.getFileUrl(path, FileServiceTypeEnum.LOCAL);
    }
    @Override
    public ResponseDTO<UploadVO> aliYunUpload(MultipartFile file, @PathVariable Integer moduleType) throws Exception {
        return fileService.fileUpload(file, FileServiceTypeEnum.ALI_OSS, moduleType);
    }
    @Override
    public ResponseDTO<String> aliYunGet(String path) {
        return fileService.getFileUrl(path, FileServiceTypeEnum.ALI_OSS);
    }
    @Override
    public ResponseDTO<UploadVO> qiNiuUpload(MultipartFile file, @PathVariable Integer moduleType) throws Exception {
        return fileService.fileUpload(file, FileServiceTypeEnum.QI_NIU_OSS, moduleType);
    }
    @Override
    public ResponseDTO<String> qiNiuGet(String path) {
        return fileService.getFileUrl(path, FileServiceTypeEnum.QI_NIU_OSS);
    }
    @Override
    public ResponseDTO<PageResultDTO<FileVO>> queryListByPage(@RequestBody FileQueryDTO queryDTO) {
        return fileService.queryListByPage(queryDTO);
    }
    @Override
    public ResponseEntity<byte[]> downLoadById(Long id, HttpServletRequest request) throws UnsupportedEncodingException {
        return fileService.downLoadById(id, request);
    }

    @Override
    public ResponseDTO<String> saveFile(@Valid @RequestBody FileAddDTO addDTO) {
        RequestTokenBO requestToken = RequestTokenUtil.getRequestUser();
        return fileService.saveFile(addDTO,requestToken);
    }
}
