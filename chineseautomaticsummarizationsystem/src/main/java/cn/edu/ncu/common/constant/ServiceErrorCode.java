package cn.edu.ncu.common.constant;

public enum ServiceErrorCode implements IErrorCode {
    /**
     * 错误码、错误信息
     */
    FAIL_TO_REGISTER_USER(100009,"注册用户失败"),

    FAIL_TO_LOGIN(10010, "登录失败"),

    BASE_CRUD_SERVICE_ERROR_CODE_CREATE(100000,"保存失败"),

    BASE_CRUD_SERVICE_ERROR_CODE_RETRIEVE(100001,"查询失败"),

    BASE_CRUD_SERVICE_ERROR_CODE_UPDATE(100002,"更新失败"),

    BASE_CRUD_SERVICE_ERROR_CODE_DELETE(100003,"删除失败"),

    /**
     * 在以上增加其它错误码
     */
    UNDEFINED(900000, "未定义");

    private int code;
    private String message;

    ServiceErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @param: code
     * @return: 错误信息
     * @see
     * @since
     */
    public static String msg(int code) {
        for (ServiceErrorCode errorCode : ServiceErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode.message;
            }
        }
        return UNDEFINED.message;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
