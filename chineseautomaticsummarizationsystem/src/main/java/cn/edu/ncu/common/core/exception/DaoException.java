package cn.edu.ncu.common.core.exception;



import cn.edu.ncu.common.constant.IErrorCode;

/**
 * @class DaoException
 * @classdesc 数据访问相关的基本异常
 * @author Sukaiting
 * @date 2020/8/26  16:07
 * @version 1.0.0
 * @see
 * @since
 */
public class DaoException extends AppException{
    private static final long serialVersionUID = 6576653981630781841L;

    public DaoException(IErrorCode errorCode, Throwable cause) {
        super(String.valueOf(errorCode.getCode()), errorCode.getMessage(), cause);
    }
}
