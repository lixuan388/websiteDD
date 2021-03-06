<%@page import="com.ecity.java.json.JSONObject"%>
<%@page import="com.ecity.java.web.service.ArticleService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
JSONObject PageData=new JSONObject();
PageData.put("title", "大巅官网");
PageData.put("keywords", "");
PageData.put("description", "");
PageData.put("PageType", "Page");
PageData.put("PageParamter", "0");
if (request.getParameter("Content") != null){

  PageData.put("PageType", "Content");
  
  String ID = (String) (request.getParameter("Content"));
  ArticleService svc=new ArticleService();
  JSONObject json=svc.GetArticle(ID, false);

  if (!json.has("Data")){
    JSONObject Data=new JSONObject();
    Data.put("Caption","文章不存在！");    
    PageData.put("ContentData", Data);
  }
  else{
    JSONObject Data=json.getJSONObject("Data");
    PageData.put("ContentData", Data);

    PageData.put("title", Data.get("Caption"));
    PageData.put("keywords", Data.get("KeyWord"));
    PageData.put("description", Data.get("Abstract"));
  }
}
else if (request.getParameter("page") != null){
  PageData.put("PageType", "Page");
  PageData.put("PageParamter", (String) (request.getParameter("page")));
}
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title><%=PageData.get("title")%></title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">

<meta name="keywords" content="<%=PageData.get("keywords")%>">
<meta name="description" content="<%=PageData.get("description")%>">
<meta name="description" content="大巅科技作为MCN机构，是新形态数字品牌运营公司。">
<meta name="description" content="提供移动互联网全平台营销服务，涵盖新媒体代运营、IP孵化、社交电商、私域流量运营、短视频营销、抖音代运营等">
<meta name="description" content="抖音代运营,新媒体运营,社群,社群运营,,社群营销,社交电商,MCN机构,短视频,短视频营销,短视频制作">


<script src="/Res/js/jquery.min.js"></script>
<script>
/*
        var mobileAgent = new Array("iphone", "ipod", "android", "mobile", "blackberry", "webos", "incognito",
            "webmate", "bada", "nokia", "lg", "ucweb", "skyfire");
        var browser = navigator.userAgent.toLowerCase();
        var isMobile = false;
        for(var i = 0; i < mobileAgent.length; i++) {
            if(browser.indexOf(mobileAgent[i]) != -1) {
                isMobile = true;
            }
        }
        if(isMobile){
            console.log("手机端");
            window.location.href = 'Mobileindex.html';
        }
*/
    </script>
    


<link rel="stylesheet" href="assets/layui/css/layui.css" media="all">
<link rel="stylesheet" href="assets/style/index.css?d=20190715" media="all">

<script type="text/javascript" src="https://s5.cnzz.com/z_stat.php?id=1277228488&web_id=1277228488"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/views/index.css" media="all">
</head>
<body class="layui-layout-body">
  <div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
      <div class="layui-header">
        <!-- 
        <div class="layui-logo" lay-hrefX="">
          <img src="../image/logo.png" alt="大巅" style="width: 40px; height: 40px;"> <span style="font-size: 1.5em; color: #fff;">大巅</span>
        </div>
        -->
        <!-- 
        <ul class="layui-nav layui-layout-left">
          <li class="layui-nav-item layadmin-flexible" lay-unselect><a href="javascript:;" layadmin-event="flexible" title="侧边伸缩" lay-tips="侧边伸缩"> <i
              class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"
            ></i>
          </a></li>
          <li class="layui-nav-item" lay-unselect><a href="javascript:;" layadmin-event="refresh" title="刷新"> <i class="layui-icon layui-icon-refresh-3"></i>
          </a></li>
        </ul>
         -->
        <!-- 头部区域 -->
        <div class="layui-side1 layui-side-menu1">
          <div class="layui-side-scroll1">
            <ul class="layui-nav-temp layui-layout-right" lay-filter="layadmin-layout-right"></ul>
          </div>
        </div>
      </div>
      <!-- 主体内容 -->
      <div class="layui-body" id="LAY_app_body"style="height:calc(100vh - 40px - 40px);width: 100%; ">
          <%
           if (PageData.get("PageType").equals("Page")){
          %>
          <div class="layadmin-tabsbody-item" style=" height: 100%;width: 100%; overflow: hidden;">
            <iframe src="<%=request.getContextPath()%>/views/page.html#/<%=PageData.get("PageParamter")%>" frameborder="0" class="layadmin-iframe" style="height: 100%; width: 100%; overflow: hidden;"></iframe>
            
          </div>
          <%          
          } else if (PageData.get("PageType").equals("Content")){            
          %>          
          <div class="layadmin-tabsbody-item" >
            <div class="ContentDiv" >
              <div>
                <h1><%=PageData.getJSONObject("ContentData").getString("Caption")%></h1>  
              </div>
              <div>
                <%=PageData.getJSONObject("ContentData").getString("Content")%>
              </div>   
            </div>
          </div>
          <%
          }
          %>
        
        
      </div>
      <div class="layui-footer">
        <!-- 底部固定区域 -->
        <span>Copyright © 2019  DADIANKEJI.CN</span>
        <span>粤ICP备19093257号-1</span>
      </div>
    </div>
      
  
    <!-- 辅助元素，一般用于移动设备下遮罩 -->
    <div class="layadmin-body-shade" layadmin-event="shade"></div>
  </div>
  <script src="assets/layui/layui.js"></script>
  <script>
  layui.config({
    base: 'assets/' //静态资源所在路径
  }).extend({  
    index: 'lib/index' //主入口模块
  }).use('index');
  </script>
</body>
</html>