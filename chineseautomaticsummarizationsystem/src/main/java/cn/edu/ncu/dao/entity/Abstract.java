package cn.edu.ncu.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.util.Objects;

@ApiModel(value="cn.edu.ncu.dao.entity.Abstract")
@Table(name = "abstract")
public class Abstract {
    /**
     * 文摘主键
     */
    @ApiModelProperty(value="id文摘主键")
    @Column(name="id")
    @Id
    private Long id;

    /**
     * 文章标题
     */
    @ApiModelProperty(value="title文章标题")
    private String title;

    /**
     * 对应的上传论文文件id
     */
    @ApiModelProperty(value="fileId对应的上传论文文件id")
    private Long fileId;

    /**
     * 自动摘要内容
     */
    @ApiModelProperty(value="abstractContent自动摘要内容")
    private String abstractContent;

    /**
     * 文摘主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 文摘主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 文章标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 文章标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 对应的上传论文文件id
     */
    public Long getFileId() {
        return fileId;
    }

    /**
     * 对应的上传论文文件id
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    /**
     * 自动摘要内容
     */
    public String getAbstractContent() {
        return abstractContent;
    }

    /**
     * 自动摘要内容
     */
    public void setAbstractContent(String abstractContent) {
        this.abstractContent = abstractContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Abstract that = (Abstract) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(fileId, that.fileId) &&
                Objects.equals(abstractContent, that.abstractContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, fileId, abstractContent);
    }

    @Override
    public String toString() {
        return "Abstract{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", fileId=" + fileId +
                ", abstractContent='" + abstractContent + '\'' +
                '}';
    }
}