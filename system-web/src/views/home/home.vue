<template>
    <div class="bg fontset">
      <div class="center">
          <h2>毕业设计题目:中文文献自动摘要系统设计与实现</h2>
        </div>
    <div class="center">专业：软件学院-软件工程&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;指导老师：胡军&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本人：熊志聪</div>
    <div class=" center"><img alt src="http://www.ncu.edu.cn/img/nculogo.jpg" width="200px"/></div>
    <div class="center"><img alt src="@/assets/images/project.png" /></div>
    
  <h3>基本介绍</h3>
  <p class="fontset">&nbsp;&nbsp;中文文献自动摘要系统是一个包含了许多基础功能的文献摘要系统,包括通信,权限设置,系统设置,文件管理,在线日志查看,心跳服务,任务管理等。这样的系统不仅仅能够满足最基本的自动摘要生成,而且还提供了许多
    很有用的服务。</p>
  <p>&nbsp;&nbsp;系统结构是B/S,使用了最前沿的技术栈SpringBoot+Vue,是一个前后端分离的项目。</p>
  <h3>技术体系</h3>
  <ul class="ulMarginSet">
    <li>前端：<el-tag type="success" class="tagSize">Vue</el-tag><el-tag type="info" class="tagSize">Vue-Route</el-tag>
              <el-tag type="warning" class="tagSize">vue-enum</el-tag><el-tag type="success" class="tagSize">element-ui</el-tag>
    </li>
    <li>后端：<el-tag type="success" class="tagSize">SpringBoot</el-tag><el-tag type="warning" class="tagSize">Mybatis</el-tag>
             <el-tag type="danger" class="tagSize">jwt</el-tag><el-tag type="info" class="tagSize">druid</el-tag><el-tag type="warning" class="tagSize">MySql</el-tag>
             <el-tag type="success" class="tagSize">Redis</el-tag></li>
  </ul>
  <h3>前端特点</h3>
  <ul class="ulMarginSet">
    <li>高质量的项目代码和代码结构</li>
    <li>界面优美,菜单栏,标签页等的设计和用户的交互性很好</li>
    <li>界面缓存正确操作,上传文件时不缓存,切换界面时缓存</li>
    <li>权限设计,让没有对应界面操作的不显示</li>
    <li>多级菜单</li>
  </ul>
  <h3>后端特点</h3>
  <ul class="ulMarginSet">
    <li>高质量的Java代码和项目结构,详尽的代码注释</li>
    <li>JWT用户身份认证</li>
    <li>响应信息格式统一</li>
    <li>权限验证基于注解实现,使用起来很是便捷</li>
    <li>项目结构是四层(controller,service,model,dao)</li>
    <li>Redis的使用使得性能的提升</li>
    <li>动态代码加载,让一些代码可以在不重启项目的情况下再次执行</li>
    <li>心跳服务,发现正在运行本项目的机器有哪些</li>
    <li>基于websocket的在线用户信息统计</li>
    <li>完整的前后端交互日志,拦截器会将控制层接受到的信息拦截显示</li>
  </ul>
  <h3 style="text-align:center;">自动摘要算法实现流程图</h3>
  <img alt="还未上传图片" src="@/assets/images/algorithm.png" width="50%" style="margin-left:30%" /><br/>
  <el-link type="primary" href="https://github.com/StarForAll/Graduate-Designing" >本项目Github地址</el-link>
  </div>
</template>

<script>
export default {
  name: 'Home',
  props: {},
  data () {
    return {
    };
  },
  computed: {},
  watch: {},
  filters: {},
  created () {},
  mounted () {},
  beforeCreate () {},
  beforeMount () {},
  beforeUpdate () {},
  updated () {},
  beforeDestroy () {},
  destroyed () {},
  activated () {},
  methods: {
  }
};
</script>

<style lang="less">
.el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
  .center{
    text-align:center;
  }
  .fontset{
    font-family: "楷体";
    font-size: 20px;
  }
  .ulMarginSet{
    margin-left: 50px;
  }
  .tagSize{
    // width:100px;
    font-size: 20px;
    margin-right: 20px;
    // text-align:center;
  }
  .bg{
    // background:url("../../assets/images/systembg.jpeg") no-repeat;
    // background-size:100% 100%; 
    // background-attachment: fixed;
  }
</style>
