package cn.edu.ncu.dao.entity;

import javax.persistence.Transient;
import java.util.Objects;

/**
 * @Author: XiongZhiCong
 * @Description: 摘要信息
 * @Date: Created in 8:21 2021/4/21
 * @Modified By:
 */
public class AbstractInfo {
    @Transient
    private Abstract anAbstract;
    @Transient
    private File file;

    public AbstractInfo() {
    }

    public AbstractInfo(Abstract anAbstract, File file) {
        this.anAbstract = anAbstract;
        this.file = file;
    }

    public Abstract getAnAbstract() {
        return anAbstract;
    }

    public void setAnAbstract(Abstract anAbstract) {
        this.anAbstract = anAbstract;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractInfo that = (AbstractInfo) o;
        return Objects.equals(anAbstract, that.anAbstract) &&
                Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anAbstract, file);
    }

    @Override
    public String toString() {
        return "AbstractInfo{" +
                "anAbstract=" + anAbstract +
                ", file=" + file +
                '}';
    }

}
