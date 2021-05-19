<template>
    <div>
        <Card class="warp-card" dis-hover>
            <Alert>
                <h3>中文自动摘要系统  上传论文文件格式介绍：</h3>
                <pre>
 <label class="labelAlert">要求1:必须是txt文本,且是UTF-8字符集编码
 要求2:论文文本内容必须符合一定格式要求。
· 第一行必须要是论文标题,且不能有其他无关字符。
· 标题或者是小标题必须以阿拉伯数字或者是中文数字开头。
· 自然段前有两个空格。
· 不需要图片和相关描述信息。
· 不需要参考文献。
· ※强烈推荐※ 下载"上传论文样例.txt"用以参考。
                  </label>
                </pre>
                <el-link type="primary" v-on:click="downloadExampleFile()">下载样例论文</el-link>
                <img width="1000" style="margin-left:100px"   :src="paperExampleImgUrl" alt="系统参数中upload_paper_image参数未设置" />
            </Alert>
            <Form :model="searchForm" class="tools" inline ref="searchForm">
                <FormItem prop="title">
                    <Input placeholder="请输入论文标题搜索" type="text" v-model="searchForm.title" />
                </FormItem>
                <FormItem>
                    <ButtonGroup>
                        <Button
                        @click="find"
                        :headers="uploadHeader"
                        icon="ios-search"
                        type="primary"
                        v-privilege="'summary-summaryPage-query'"
                        >查询</Button>
                        <Button
                        @click="reset"
                        icon="md-refresh"
                        type="default"
                        v-privilege="'summary-summaryPage-query'"
                        >重置</Button>
                    </ButtonGroup>
                </FormItem>
                <FormItem v-privilege="'summary-summaryPage-upload'">
                    <Upload
                        :action="uploadUrl"
                        :headers="uploadHeader"
                        :onError="uploadError"
                        :onSuccess="uploadSuccess"
                        :showUploadList="false"
                        accept=".txt"
                    >
                    <Button icon="ios-cloud-upload-outline" type="primary">上传新文件</Button>
                    </Upload>
                </FormItem>
            </Form>
            <Table :columns="columns" :data="tableData" :loading="tableLoading"></Table>
            <Page
                :current="searchForm.pageNum"
                :page-size="searchForm.pageSize"
                :page-size-opts="[10, 20, 30, 50, 100]"
                :total="total"
                @on-change="changePage"
                @on-page-size-change="changePageSize"
                show-elevator
                show-sizer
                show-total
                style="margin:24px 0;text-align:right;"
            ></Page>
        </Card>
        
          <el-dialog :title="showTitle" width="75%" :visible.sync="editModal">
              <p class="showContent">{{summaryContent}}</p>
              <div slot="footer" class="dialog-footer">
                <el-button type="primary" plain icon="el-icon-s-management" @click="cancelSave">自动摘要信息已阅读</el-button>
             </div>
          </el-dialog>
          
        <div >
        </div>
 
    </div>
    
</template>

<script>
import { textSummarizationApi } from '@/api/text-summarization'
import { fileApi } from '@/api/file.js';
import Cookies from 'js-cookie';
import { TOKEN_KEY } from '@/lib/cookie';
export default {
    // name: 'summaryList',
    components: {},
    props: {},
    data() {
        return {
            dialogTableVisible: false,
            paperExampleImgUrl:'',
            paperExampleTxtId:-1,
            summaryContent:'',
            editModal: false,
            showTitle:'',
            saveLoading: true,
            // 数据量
            total: null,
            // 文件表格加载
            tableLoading: false,
            abstractInfoDelete: {
              id: -1,
              fileId:-1
            },
            // 查询参数
            searchForm: {
                pageNum: 1,
                pageSize: 10,
                // 是否查询总条数
                searchCount: true,
                //论文标题
                title: null,
            },
            // 表头
            columns: [
                {
                  title: '论文标题',
                  render: (h, params) =>{
                    return h('span', {
                      style: {
                          display: 'inline-block',
                          width: '100%',
                          margin: '10px',
                          overflow: 'hidden',
                          textOverflow: 'hiddden'
                      }
                    }, params.row.anAbstract.title)
                  }
                },
                {
                  title: '文件名称',
                  //key: 'row.file.fileName'
                  render: (h, params) =>{
                    return h('span', {
                      style: {
                          display: 'inline-block',
                          width: '50%',
                          margin: '10px',
                          overflow: 'hidden',
                          textOverflow: 'hiddden'
                      }
                    }, params.row.file.fileName)
                  }
                },
                {
                  title: '上传时间',
                 //key: 'row.file.createTime'
                  render: (h, params) =>{
                    return h('span', {
                      style: {
                          display: 'inline-block',
                          width: '50%',
                          margin: '10px',
                          overflow: 'hidden',
                          textOverflow: 'hiddden'
                      }
                    }, params.row.file.createTime)
                  }
                },
                {
                  title: '操作',
                  // key: 'action',
                  width: 400,
                  align: 'center',
                  // className: 'action-hide',
                  render: (h, params) => {
                      return this.$tableAction(h, [
                      {
                          title: '下载',
                          directives: [
                          {
                              name: 'privilege',
                              value: 'summary-summaryPage-upload'
                          }
                          ],
                          action: () => {
                          this.downloadFile(params.row.anAbstract.fileId);
                          }
                      },
                      {
                          title: '查看自动摘要内容',
                          directives: [
                          {
                              name: 'privilege',
                              // 这里需要自己在route定义
                              value: 'summary-summaryPage-getContent'
                          }
                          ],
                          action: () => {
                          this.querySummaryContent(params.row.anAbstract.id,params.row.anAbstract.title);
                          }
                      },
                      {
                          title: '删除',
                          directives: [
                          {
                              name: 'privilege',
                              // 这里需要自己在route定义
                              value: 'summary-summaryPage-delete'
                          }
                          ],
                          action: () => {
                          this.deleteSummary(params.row.anAbstract.id,params.row.anAbstract.fileId);
                          }
                      }
                      ]);
                  }
                }
            ],
            // table数据
            tableData: []
        }
    },
    computed: {
    uploadHeader: function() {
      let header = {
        'x-access-token': Cookies.get(TOKEN_KEY)
      };
      return header;
    },
    uploadUrl: function() {
        return textSummarizationApi.getSummaryTextUrlFirst;
    },
  },
  created() {
    this.loadParams();
    this.find();
  },
  methods:{
      // 上传成功钩子
    async uploadSuccess(e) {
      if (!e.success) {
        console.error(e);
        return this.uploadError();
      }
      this.$Message.success(e.msg);
      this.find();
    },
    // 上传失败钩子
    uploadError(e) {
      this.$Message.error('上传出错，请重试！');
      console.error(e);
      this.find();
    },
      //加载页面出现时需要的参数
      async loadParams() {
      try {
        this.tableLoading = true;
        let result = await textSummarizationApi.loadParams();
        this.paperExampleImgUrl = result.data.paperExampleImgUrl;
        this.paperExampleTxtId = (result.data.paperExampleTxtId);
        this.tableLoading = false;
      } catch (e) {
        //TODO zhuoda sentry
        console.error(e);
        this.tableLoading = false;
      }
    },
    // 下载文件
    downloadFile(id) {
      fileApi.downLoadFile(id);
    },
    //下载论文样例
    downloadExampleFile(){
      console.log("样例文件id:"+this.paperExampleTxtId);
      fileApi.downLoadFile(this.paperExampleTxtId);
    },
    // 重置
    reset() {
      this.searchForm.title = null;
      this.$refs.searchForm.resetFields();
      this.find();
    },
    // 查询
    find() {
      this.searchForm.pageNum = 1;
      this.searchForm.pageSize = 10;
      this.getSummaryList();
    },
    // 更改分页查询条数
    changePageSize(pageSize) {
      this.searchForm.pageNum = 1;
      this.searchForm.pageSize = pageSize;
      this.getSummaryList();
    },
    // 获取文件数据
    async getSummaryList() {
      try {
        this.tableLoading = true;
        let res = await textSummarizationApi.queryAbstractInfo(this.searchForm);
        this.tableData = res.data.list;
        console.log("data "+this.tableData[0].file.createTime);
        this.total = parseInt(res.data.total);
        this.tableLoading = false;
      } catch (e) {
        //TODO zhuoda sentry
        console.error(e);
        this.tableLoading = false;
      }
    },
    // 页码改变
    changePage(pageNum) {
      this.searchForm.pageNum = pageNum;
      this.getSummaryList();
    },
    async querySummaryContent(id,title){
      try {
        this.editModal = true;
        let res = await textSummarizationApi.querySummaryContent(id);
        this.showTitle=title+'的自动摘要信息内容';
        this.summaryContent = res.data;
      } catch (e) {
        //TODO zhuoda sentry
        console.error(e);
        this.editModal = false;
      }
    },
    // 关闭自动摘要信息界面
    cancelSave() {
      this.editModal = false;
      this.find();
    },
    async deleteSummary(id,fileId){
      try {
        this.abstractInfoDelete.id=id;
        this.abstractInfoDelete.fileId=fileId;
        let res = await textSummarizationApi.deleteAbstractInfo(this.abstractInfoDelete);
        let deleteMsg = res.msg;
        this.$Message.success(deleteMsg);
        this.find();
      } catch (e) {
        //TODO zhuoda sentry
        console.error(e);
        this.$Message.error(e);
      }
    }
  }
};
</script>
<style>
        .labelAlert{
            font-size: 20px;
            font-family: "楷体";
            color:rgba(44, 13, 219, 0.966);
        }
        .showContent{
          font-size: 20px;
            font-family: "楷体";
        }
    </style>