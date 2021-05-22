package cn.edu.ncu.common.core.exception;



import cn.edu.ncu.common.constant.IErrorCode;

/**
 * @Author: XiongZhiCong
 * @Description: 数据访问相关的基本异常
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class DaoException extends AppException{
    private static final long serialVersionUID = 6576653981630781841L;

    public DaoException(IErrorCode errorCode, Throwable cause) {
        super(String.valueOf(errorCode.getCode()), errorCode.getMessage(), cause);
    }
}
