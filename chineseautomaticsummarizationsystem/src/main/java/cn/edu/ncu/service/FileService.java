package cn.edu.ncu.service;

import cn.edu.ncu.common.constant.FileServiceTypeEnum;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
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
public interface FileService {



    /**
     * 文件上传服务
     *
     * @param file
     * @param typeEnum   文件服务类型枚举类
     * @param moduleType 文件夹类型
     * @return
     */
     ResponseDTO<UploadVO> fileUpload(MultipartFile file, FileServiceTypeEnum typeEnum, Integer moduleType);

    /**
     * 根据文件绝对路径 获取文件URL
     *
     * @param path
     * @return
     */
     ResponseDTO<String> getFileUrl(String path, FileServiceTypeEnum typeEnum) ;

    /**
     * 批量插入
     *
     * @param fileDTOList
     */
     void insertFileBatch(List<FileDTO> fileDTOList);

    /**
     * 根据module 删除文件信息
     *
     * @param moduleId
     * @return
     */
     void deleteFilesByModuleId(String moduleId);

    /**
     * 根据module 获取文件信息
     *
     * @param moduleId
     * @return
     */
     List<FileVO> listFilesByModuleId(String moduleId);

    /**
     * @param filesStr 逗号分隔文件id字符串
     * @return
     */
     List<FileVO> getFileDTOList(String filesStr);

    /**
     * 分页查询文件列表
     *
     * @param queryDTO
     * @return
     */
     ResponseDTO<PageResultDTO<FileVO>> queryListByPage(FileQueryDTO queryDTO);

    /**
     * 根据id 下载文件
     *
     * @param id
     * @param request
     * @return
     */
     ResponseEntity<byte[]> downLoadById(Long id, HttpServletRequest request) throws UnsupportedEncodingException;

    /**
     * 系统文件保存通用接口
     * @param addDTO
     * @return
     */
     ResponseDTO<String> saveFile(FileAddDTO addDTO, RequestTokenBO requestToken);
}
