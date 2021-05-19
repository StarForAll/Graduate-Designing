package cn.edu.ncu.common.constant;

public enum DaoErrorCode implements IErrorCode  {

    /**
     * 错误码、错误信息
     */

    FAIL_TO_CREATE(200009,"增加失败"),

    FAIL_TO_DELETE(200010, "删除失败"),

    FAIL_TO_UPDATE(200011,"修改失败"),

    FAIL_TO_QUERY(200012,"查询失败"),

    /**
     * 在以上增加其它错误码
     */
    UNDEFINED(900000, "未定义");

    private int code;
    private String message;

    DaoErrorCode (int code, String message) {
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
        for (DaoErrorCode errorCode : DaoErrorCode.values()) {
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
