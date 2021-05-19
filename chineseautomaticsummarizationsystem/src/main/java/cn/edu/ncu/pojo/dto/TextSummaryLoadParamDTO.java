package cn.edu.ncu.pojo.dto;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 20:02 2021/4/21
 * @Modified By:
 */
public class TextSummaryLoadParamDTO {
    private Long paperExampleTxtId;
    private String paperExampleImgUrl;

    public TextSummaryLoadParamDTO() {
    }

    public TextSummaryLoadParamDTO(Long paperExampleTxtId, String paperExampleImgUrl) {
        this.paperExampleTxtId = paperExampleTxtId;
        this.paperExampleImgUrl = paperExampleImgUrl;
    }

    public Long getPaperExampleTxtId() {
        return paperExampleTxtId;
    }

    public void setPaperExampleTxtId(Long paperExampleTxtId) {
        this.paperExampleTxtId = paperExampleTxtId;
    }

    public String getPaperExampleImgUrl() {
        return paperExampleImgUrl;
    }

    public void setPaperExampleImgUrl(String paperExampleImgUrl) {
        this.paperExampleImgUrl = paperExampleImgUrl;
    }
}
