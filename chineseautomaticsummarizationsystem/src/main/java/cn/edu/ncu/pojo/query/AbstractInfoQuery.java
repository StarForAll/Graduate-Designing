package cn.edu.ncu.pojo.query;

import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;

import java.util.Objects;

/**
 * @Author: XiongZhiCong
 * @Description: 摘要查询条件
 * @Date: Created in 8:40 2021/4/21
 * @Modified By:
 */
public class AbstractInfoQuery extends PageParamDTO {
    private String title;
    private Long userId;
    private String moduleType;

    public AbstractInfoQuery() {
    }

    public AbstractInfoQuery(String title, Long userId, String moduleType) {
        this.title = title;
        this.userId = userId;
        this.moduleType = moduleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractInfoQuery that = (AbstractInfoQuery) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(moduleType, that.moduleType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, userId, moduleType);
    }

}
