package cn.edu.ncu.pojo.object;


import cn.edu.ncu.dao.entity.ReloadItem;
import cn.edu.ncu.dao.entity.ReloadResult;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * AbstractReloadObject 处理程序的抽象类
 *
 * @author zhuoda
 */
public abstract class AbstractReloadObject {

    protected String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 通过reloadItem参数reload，获得结果
     *
     * @param reloadItem
     * @return boolean
     * @author zhuokongming
     * @date 2016年10月21日 下午2:09:44
     */
    public abstract ReloadResult reload(ReloadItem reloadItem);
}
