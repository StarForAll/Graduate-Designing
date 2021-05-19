package cn.edu.ncu.common.core.exception;


import cn.edu.ncu.common.constant.ResponseCodeConst;
import cn.edu.ncu.common.constant.BusinessErrorCode;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.util.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: XiongZhiCong
 * @Description: 全局异常处理器
 *               维护历史记录 : 增加了 ResponseBodyAware 对 分页方法进行自动判断调用
 * @Date: Created in 14:33 2021/4/8
 * @Modified By:
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerResolver {

    private static final int BIND_EXCEPTION_CODE = 300001;
    private static final String BIND_EXCEPTION_MESSAGE = "参数错误";

    private GlobalControllerResolver() {
        // no-args constructor
    }
    @ExceptionHandler(Exception.class)
    public ResponseDTO<Object> businessException(Exception exception) {
        log.error("业务异常：{}, 堆栈：{}",
                ExceptionUtil.getAllExceptionMsg(exception),exception);
        // http 请求方式错误
        if (exception instanceof HttpRequestMethodNotSupportedException) {
            return ResponseDTO.wrap(ResponseCodeConst.REQUEST_METHOD_ERROR);
        }

        // 参数类型错误
        if (exception instanceof TypeMismatchException) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }

        // json 格式错误
        if (exception instanceof HttpMessageNotReadableException) {
            return ResponseDTO.wrap(ResponseCodeConst.JSON_FORMAT_ERROR);
        }

        // 参数校验未通过
        if (exception instanceof MethodArgumentNotValidException) {
            List<FieldError> fieldErrors = ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors();
            List<String> msgList = fieldErrors.stream().map(FieldError :: getDefaultMessage).collect(Collectors.toList());
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, String.join(",", msgList));
        }

        if (exception instanceof BusinessException) {
            return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_ERROR);
        }

        return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_ERROR);

    }

}
