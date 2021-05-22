package cn.edu.ncu.service.exception;


import cn.edu.ncu.common.constant.IErrorCode;

/**
 * @Author: XiongZhiCong
 * @Description: 业务层异常码
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public enum BaseDataExceptionCode implements IErrorCode {
    /**
     *
     */
    STOP_WORD_DB_ADD_EXCEPTION_CODE(100001, "停用词添加异常"),
    DES_EXCEPTION_CODE(100002, "DES异常"),
    ID_GENERATOR_CODE(100003,"IdGenerator， id 数据库不存在"),
    ID_GENERATOR_TOO_SHORT_CODE(100004,"IdGenerator，step过短"),
    ID_GENERATOR_NOT_FOUND_CODE(100005,"IdGenerator， id 不存在"),
    SYSTEM_CONFIG_LACK(100006,"缺少系统配置项");


    /**
     * 错误码
     */
    private int code;
    /**
     * 错误消息
     */
    private String message;

    BaseDataExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
