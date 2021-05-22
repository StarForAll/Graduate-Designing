package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;


/**
 * @Author: XiongZhiCong
 * @Description: 文件实体类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Table(name= "file")
public class File extends BaseEntity {


    /**
     * 相关业务id
     */
    private String moduleId;
    /**
     * 相关业务类型
     */
    private String moduleType;
    /**
     * 文件位置类型
     */
    private Integer fileLocationType;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 文件类型，程序中枚举控制，文件类型：如身份证正面，三证合一等等
     */
    private String fileType;
    /**
     * 文件key，用于文件下载
     */
    private String filePath;
    /**
     * 创建人，即上传人
     */
    private Long createrUser;

    public File() {
    }

    public File(String moduleId, String moduleType, Integer fileLocationType, String fileName, String fileSize, String fileType, String filePath, Long createrUser) {
        this.moduleId = moduleId;
        this.moduleType = moduleType;
        this.fileLocationType = fileLocationType;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.filePath = filePath;
        this.createrUser = createrUser;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public Integer getFileLocationType() {
        return fileLocationType;
    }

    public void setFileLocationType(Integer fileLocationType) {
        this.fileLocationType = fileLocationType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getCreaterUser() {
        return createrUser;
    }

    public void setCreaterUser(Long createrUser) {
        this.createrUser = createrUser;
    }

}

