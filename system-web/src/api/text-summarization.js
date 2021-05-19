import { postAxios, getAxios } from '@/lib/http';
import config from '@/config';
const baseUrl = config.baseUrl.apiUrl;

export const textSummarizationApi = {
    // 加载初始化参数到前端
    loadParams: () => {
        return getAxios('/api/summary/load');
    },
    // 查询分页摘要信息
    queryAbstractInfo: (data) => {
        return postAxios('/api/summary/queryInfo', data);
    },
    // 彻底删除摘要相关信息 
    deleteAbstractInfo: (data) => {
        return postAxios('/api/summary/delete', data);
    },
    // 根据文件生成对应的文摘内容并响应操作结果--------------------  
    getSummaryText: (data) => {
        // return postAxios('/api/file/localUpload/' + type, data);
        // return postAxios('/api/summary/getSummary',data);
        return this.getSummaryTextUrl
    },
    // 根据摘要id得到对应的文摘内容 ----
    querySummaryContent: id=>{
        return getAxios('/api/summary/querySummary/'+id);
    },
    getSummaryTextUrlFirst: baseUrl + '/api/summary/getSummary/'
};
