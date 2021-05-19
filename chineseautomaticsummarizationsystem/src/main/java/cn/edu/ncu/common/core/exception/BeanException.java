package cn.edu.ncu.common.core.exception;
/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
public class BeanException extends RuntimeException{
    public BeanException(String msg){
        super(msg);
    }

    public BeanException(String msg, Throwable cause){
        super(msg, cause);
    }
}
