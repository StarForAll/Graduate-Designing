package cn.edu.ncu.common.constant;

/**
 * @Author: XiongZhiCong
 * @Description: 岗位响应常量
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class PositionResponseCodeConst extends ResponseCodeConst {

    public static final PositionResponseCodeConst REMOVE_DEFINE = new PositionResponseCodeConst(13000, "还有人关联该岗位,不能删除");

    protected PositionResponseCodeConst(int code, String msg) {
        super(code, msg);
    }

}
