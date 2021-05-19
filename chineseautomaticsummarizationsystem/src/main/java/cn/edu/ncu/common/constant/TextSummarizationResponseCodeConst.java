package cn.edu.ncu.common.constant;

/**
 * @Author: XiongZhiCong
 * @Description: 文本摘要对应的响应常量
 * @Date: Created in 16:59 2021/4/20
 * @Modified By:
 */
public class TextSummarizationResponseCodeConst extends ResponseCodeConst{
    public static final TextSummarizationResponseCodeConst UPLOAD_FILE_EXIST = new TextSummarizationResponseCodeConst(9001, "上传文本存在，不能重复上传",false);

    protected TextSummarizationResponseCodeConst(int code, String msg,boolean success) {
        super(code, msg,success);
    }
}
