package cn.edu.ncu.pojo.delete;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 10:08 2021/4/21
 * @Modified By:
 */
public class AbstractInfoDelete {
    @NotNull(message = "摘要id不能为空")
    private Long id;
    @NotNull(message = "文件id不能为空")
    private Long fileId;

    public AbstractInfoDelete() {
    }

    public AbstractInfoDelete(Long id, Long fileId) {
        this.id = id;
        this.fileId = fileId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractInfoDelete that = (AbstractInfoDelete) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fileId, that.fileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileId);
    }

}
