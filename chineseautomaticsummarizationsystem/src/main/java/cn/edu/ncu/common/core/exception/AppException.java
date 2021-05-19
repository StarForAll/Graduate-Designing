package cn.edu.ncu.common.core.exception;
/**
 * @Author: XiongZhiCong
 * @Description:     基于异常码的方式统一了系统异常，对于不稳定的业务方法要求必须捕获异常抛出
 *                  异常 全局要求对此类异常做日志记录和构建统一的应答给前端
 * @Date: Created in 14:33 2021/4/8
 * @Modified By:
 */
public class AppException extends RuntimeException{

    private static final long serialVersionUID = -3607818880814070092L;
    /**
     * 异常码
     */
    private String code;

    public AppException() {

    }

    public AppException(String code, String message) {
        super(message);
        this.code = code;
    }

    public AppException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
