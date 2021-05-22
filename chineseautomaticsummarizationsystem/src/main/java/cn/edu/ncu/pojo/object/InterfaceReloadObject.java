package cn.edu.ncu.pojo.object;


import cn.edu.ncu.dao.entity.ReloadItem;
import cn.edu.ncu.dao.entity.ReloadResult;
import cn.edu.ncu.service.Reloadable;

/**
 * @Author: XiongZhiCong
 * @Description: Reload 处理程序的实现类；用于处理以接口实现的处理类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class InterfaceReloadObject extends AbstractReloadObject {

    private Reloadable object;

    public InterfaceReloadObject(Reloadable object) {
        super();
        this.object = object;
    }

    @Override
    public ReloadResult reload(ReloadItem reloadItem) {
        ReloadResult reloadResult = new ReloadResult();
        reloadResult.setArgs(reloadItem.getArgs());
        reloadResult.setIdentification(reloadItem.getIdentification());
        reloadResult.setTag(reloadItem.getTag());
        try {
            boolean res = object.reload(reloadItem);
            reloadResult.setResult(res);
        } catch (Throwable e) {
            reloadResult.setException(getStackTrace(e));
        }
        return reloadResult;
    }

}
