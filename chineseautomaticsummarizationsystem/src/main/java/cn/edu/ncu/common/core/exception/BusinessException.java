package cn.edu.ncu.common.core.exception;

import cn.edu.ncu.common.constant.IErrorCode;

/**
 * @Author: XiongZhiCong
 * @Description: 基本业务操作异常，所有业务操作异常都继承于该类
 * @Date: Created in 14:33 2021/4/8
 * @Modified By:
 */
public class BusinessException extends AppException{
    private static final long serialVersionUID = 6304058622501786159L;

    public BusinessException(IErrorCode errorCode, Throwable cause) {
        super(String.valueOf(errorCode.getCode()), errorCode.getMessage(), cause);
    }
}
