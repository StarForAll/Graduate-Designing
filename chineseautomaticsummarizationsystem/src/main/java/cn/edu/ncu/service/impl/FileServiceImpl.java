package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.FileModuleTypeEnum;
import cn.edu.ncu.common.constant.FileResponseCodeConst;
import cn.edu.ncu.common.constant.FileServiceTypeEnum;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.util.basic.BaseEnumUtil;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.File;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.dto.FileAddDTO;
import cn.edu.ncu.pojo.dto.FileDTO;
import cn.edu.ncu.pojo.dto.FileQueryDTO;
import cn.edu.ncu.pojo.vo.FileVO;
import cn.edu.ncu.pojo.vo.UploadVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import cn.edu.ncu.dao.mapper.FileMapper;
import cn.edu.ncu.service.FileService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date
 * @since JDK1.8
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileMapper fileMapper;

    @Autowired
    private java.util.Map<String, IFileService> fileServiceMap;
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    /**
     * 获取文件服务实现
     *
     * @param typeEnum
     * @return
     */
    private IFileService getFileService(FileServiceTypeEnum typeEnum) {
        /**
         * 获取文件服务
         */
        String serviceName = typeEnum.getServiceName();
        IFileService fileService = fileServiceMap.get(serviceName);
        if (null == fileService) {
            throw new RuntimeException("未找到文件服务实现类：" + serviceName);
        }
        return fileService;
    }

    /**
     * 文件上传服务
     *
     * @param file
     * @param typeEnum   文件服务类型枚举类
     * @param moduleType 文件夹类型
     * @return
     */
    @Override
    public ResponseDTO<UploadVO> fileUpload(MultipartFile file, FileServiceTypeEnum typeEnum, Integer moduleType) {
        FileModuleTypeEnum moduleTypeEnum = BaseEnumUtil.getEnumByValue(moduleType, FileModuleTypeEnum.class);
        if (null == moduleTypeEnum) {
            return ResponseDTO.wrap(FileResponseCodeConst.FILE_MODULE_ERROR);
        }
        // 获取文件服务
        IFileService fileService = this.getFileService(typeEnum);
        ResponseDTO<UploadVO> response = fileService.fileUpload(file, moduleTypeEnum.getPath());
        return response;
    }

    /**
     * 根据文件绝对路径 获取文件URL
     *
     * @param path
     * @return
     */
    @Override
    public ResponseDTO<String> getFileUrl(String path, FileServiceTypeEnum typeEnum) {
        IFileService fileService = this.getFileService(typeEnum);
        return fileService.getFileUrl(path);
    }

    /**
     * 批量插入
     *
     * @param fileDTOList
     */
    @Override
    public void insertFileBatch(List<FileDTO> fileDTOList) {
        for(FileDTO dto:fileDTOList){
            dto.setId(idGeneratorUtil.snowflakeId());
        }
        fileMapper.insertFileBatch(fileDTOList);
    }

    /**
     * 根据module 删除文件信息
     *
     * @param moduleId
     * @return
     */
    @Override
    public void deleteFilesByModuleId(String moduleId) {
        fileMapper.deleteFilesByModuleId(moduleId);
    }

    /**
     * 根据module 获取文件信息
     *
     * @param moduleId
     * @return
     */
    @Override
    public List<FileVO> listFilesByModuleId(String moduleId) {
        return fileMapper.listFilesByModuleId(moduleId);
    }

    /**
     * @param filesStr 逗号分隔文件id字符串
     * @return
     */
    @Override
    public List<FileVO> getFileDTOList(String filesStr) {
        if (StringUtils.isEmpty(filesStr)) {
            return Lists.newArrayList();
        }
        String[] fileIds = filesStr.split(",");
        List<Long> fileIdList = Arrays.asList(fileIds).stream().map(e -> Long.valueOf(e)).collect(Collectors.toList());
        List<FileVO> files = fileMapper.listFilesByFileIds(fileIdList);
        return files;
    }

    /**
     * 分页查询文件列表
     *
     * @param queryDTO
     * @return
     */
    @Override
    public ResponseDTO<PageResultDTO<FileVO>> queryListByPage(FileQueryDTO queryDTO) {
        PageResultDTO<FileVO> pageResultDTO = new PageResultDTO<>();
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=fileMapper.queryListCount(queryDTO);
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));

        List<FileVO> fileList = fileMapper.queryListByPage(queryDTO);
        if (CollectionUtils.isNotEmpty(fileList)) {
            fileList.forEach(e -> {
                // 根据文件服务类 获取对应文件服务 查询 url
                FileServiceTypeEnum serviceTypeEnum = BaseEnumUtil.getEnumByValue(e.getFileLocationType(), FileServiceTypeEnum.class);
                IFileService fileService = this.getFileService(serviceTypeEnum);
                e.setFileUrl(fileService.getFileUrl(e.getFilePath()).getData());
            });
        }
        pageResultDTO.setList(fileList);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 根据id 下载文件
     *
     * @param id
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<byte[]> downLoadById(Long id, HttpServletRequest request) throws UnsupportedEncodingException {
        File entity = fileMapper.selectByPrimaryKey(id);
        if (null == entity) {
            throw new RuntimeException("文件信息不存在");
        }

        // 根据文件服务类 获取对应文件服务 查询 url
        FileServiceTypeEnum serviceTypeEnum = BaseEnumUtil.getEnumByValue(entity.getFileLocationType(), FileServiceTypeEnum.class);
        IFileService fileService = this.getFileService(serviceTypeEnum);
        ResponseEntity<byte[]> stream = fileService.fileDownload(entity.getFilePath(), entity.getFileName(), request);
        return stream;
    }

    /**
     * 系统文件保存通用接口
     * @param addDTO
     * @return
     */
    @Override
    public ResponseDTO<String> saveFile(FileAddDTO addDTO, RequestTokenBO requestToken) {
        File entity = BeanUtil.copy(addDTO,File.class);
        if(entity.getId()==null){
            entity.setId(idGeneratorUtil.snowflakeId());
        }
        entity.setCreaterUser(requestToken.getRequestUserId());
        entity.setCreateTime(new Date());
        fileMapper.insert(entity);
        return ResponseDTO.succ();
    }
}
