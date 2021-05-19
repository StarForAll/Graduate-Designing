package cn.edu.ncu.common.core.exception;


import cn.edu.ncu.common.constant.IErrorCode;

/**
 * @Author: XiongZhiCong
 * @Description: 中间服务层异常
 * @Date: Created in 14:33 2021/4/8
 * @Modified By:
 */
public class ServiceException extends AppException {
    private static final long serialVersionUID = -8546966614620406015L;

    public ServiceException(IErrorCode errorCode, Throwable cause) {
        super(String.valueOf(errorCode.getCode()), errorCode.getMessage(), cause);
    }
}
