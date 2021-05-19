package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.File;
import cn.edu.ncu.pojo.dto.FileDTO;
import cn.edu.ncu.pojo.dto.FileQueryDTO;
import cn.edu.ncu.pojo.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cyj
 * @date 2018-01-05 上午 9:49
 */
@Mapper
public interface FileMapper extends CommonMapper<File> {

    /**
     * 批量添加上传文件
     *
     * @param fileDTOList
     * @return
     */
    Integer insertFileBatch(List<FileDTO> fileDTOList);


    /**
     * 批量添加上传文件
     *
     * @param fileDTOList
     * @return
     */
    Integer insertFileEntityBatch(List<File> fileDTOList);


    /**
     * 批量删除
     *
     * @param moduleId
     * @return
     */
    Integer deleteFilesByModuleId(@Param("moduleId") String moduleId);

    /**
     * 批量删除
     *
     * @param moduleId
     * @param moduleType
     * @return
     */
    Integer deleteFilesByModuleIdAndModuleType(@Param("moduleId") String moduleId, @Param("moduleType") String moduleType);

    /**
     * @param moduleId
     * @return
     */
    List<FileVO> listFilesByModuleId(@Param("moduleId") String moduleId);

    List<FileVO> listFilesByFileIds(@Param("fileIds") List<Long> fileIds);

    List<FileVO> listFilesByModuleIdAndModuleType(@Param("moduleId") String moduleId, @Param("moduleType") String moduleType);

    List<FileVO> listFilesByModuleIdAndModuleTypes(@Param("moduleId") String moduleId, @Param("moduleTypes") List<String> moduleTypes);

    List<FileVO> listFilesByModuleIdsAndModuleType(@Param("moduleIds") List<String> moduleIds, @Param("moduleType") String moduleType);
    Integer queryListCount(@Param("queryDTO") FileQueryDTO queryDTO);
    List<FileVO> queryListByPage(@Param("queryDTO") FileQueryDTO queryDTO);
}
