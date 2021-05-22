package cn.edu.ncu.dao.entity;
/**
 * @Author: XiongZhiCong
 * @Description: ReloadItem 类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class ReloadItem {

    /**
     * 项名称
     */
    private String tag;

    /**
     * 参数
     */
    private String args;

    /**
     * 标识
     */
    private String identification;

    public ReloadItem() {

    }
    public ReloadItem(String tag, String identification, String args) {
        this.tag = tag;
        this.identification = identification;
        this.args = args;
    }

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getIdentification() {
        return identification;
    }
    public void setIdentification(String identification) {
        this.identification = identification;
    }
    public String getArgs() {
        return args;
    }
    public void setArgs(String args) {
        this.args = args;
    }
    @Override
    public String toString() {
        return "ReloadItem{" + "tag='" + tag + '\'' + ", identification='" + identification + '\'' + ", args='" + args + '\'' + '}';
    }
}
